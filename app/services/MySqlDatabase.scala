package services

import models._
import models.db.Tables
import models.db.Tables._
import models.db.Tables.profile.api._
import otw.api.response.{FindWorkResponse, WorkFoundResponse, WorkNotFoundResponse}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object MySqlDatabase {

  val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

  // Get stuff

  // There should only be one row in the config table
  val archiveconfig: Future[ArchiveConfig] = db.run {
    for (config <- Archiveconfig.result)
      yield {
        config.headOption match {
          case Some(c) => (ArchiveConfig.apply _).tupled(Tables.ArchiveconfigRow.unapply(c).get)
          case None    =>  ArchiveConfig()
        }
      }
  }

  lazy val storiesAndChapters = Stories.joinLeft(Chapters).on(_.id === _.storyid)

  lazy val storiesAndAuthors = Stories.joinLeft(Authors).on(_.authorid === _.id)

  lazy val authorsAndStories = Authors
    .joinLeft(storiesAndChapters).on(_.id === _._1.authorid)
    .map { case ((author, storiesWithChapters)) =>
      (author, storiesWithChapters)
    }

  def authorBookmarks(id: Long) = db.run { Bookmarks.filter(_.authorid === id).result }

  def authorsWithItems(filter: String = ""): Future[Seq[AuthorWithWorks]] = db.run {

    def groupStoryResults(authorResults: Seq[(Tables.AuthorsRow, Option[(Tables.StoriesRow, Option[Tables.ChaptersRow])])]):
      Map[Author, Option[Seq[StoryWithChapters]]] =
      authorResults
        .groupBy(a => (Author.apply _).tupled(Tables.AuthorsRow.unapply(a._1).get))
        .map { row =>
          val author: Author = row._1
          val stories = {
            val maybeStoryRows = sequence(row._2.map { case (a, s) => s }.toList)
            maybeStoryRows
              .map { list =>
                list
                  .groupBy(s => (Story.apply _).tupled(Tables.StoriesRow.unapply(s._1).get))
                  .map { case (s, scs) =>
                    val chapters: List[Option[Chapter]] = scs.map { opt =>
                      opt._2.map {
                        c => (Chapter.apply _).tupled(Tables.ChaptersRow.unapply(c).get)
                      }
                    }.toList

                    StoryWithChapters(s, sequence(chapters).map(_.toList))
                  }
                  .toSeq
              }
          }
          author -> stories
        }

    for (authorResults <- authorsAndStories.result)
    yield {
      val stories = groupStoryResults(authorResults)
        stories.map { case (author, stories) =>
          val bookmarkFuture =
            authorBookmarks(author.ID).map(_.map(b => (Bookmark.apply _).tupled(Tables.BookmarksRow.unapply(b).get)))
          Await.result(bookmarkFuture.map { bookmarks =>
            AuthorWithWorks(author,
                            stories,
                            if (bookmarks.isEmpty) None else Some(bookmarks))
          }, 10 seconds)
        }.toSeq
        .filter(a => a.bookmarks.isDefined || a.stories.isDefined)
        .sortBy(_.author.name)
    }
  }

  def storyWithAuthors(id: Long) =
    for (storyResults <- storiesAndAuthors.filter(_._1.id === id).result)
    yield {
      storyResults
        .groupBy(s => (Story.apply _).tupled(Tables.StoriesRow.unapply(s._1).get))
        .map { case (s, a) =>
          StoryWithAuthors(
            s,
            a.map { aa => (Author.apply _).tupled(Tables.AuthorsRow.unapply(aa._2.get).get) }
          )
      }
    }


  def chapterWithStory(id: Long) = db.run {
    for {
      chapterResult <- Chapters.filter(_.id === id).result
      storyResult <- storyWithAuthors(chapterResult.head.storyid)
    }
    yield {
      val chapter = (Chapter.apply _).tupled(Tables.ChaptersRow.unapply(chapterResult.head).get)
      ChapterWithStory(chapter = chapter, storyWithAuthors = storyResult.head)
    }
  }

  // Do stuff
  private def sequence[T](l: Seq[Option[T]]) =
    if (l.contains(None)) None else Some(l.flatten)

  def updateStoryStatuses(findResult: FindWorkResponse, stories: Seq[StoryWithChapters]) = {

    def storyQ(id: String) = Stories.filter(_.id === id.toLong).map(x => (x.imported, x.ao3url))

    for (r <- findResult.body)
    yield {
      r match {
        case w: WorkFoundResponse => db.run(storyQ(w.originalId).update((true, Some(w.workUrl))))
        case w: WorkNotFoundResponse => db.run(storyQ(w.originalId).update((false, None)))
      }
    }
  }
}
