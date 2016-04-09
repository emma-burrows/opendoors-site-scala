package services

import models.db.Tables.profile.api._
import models._
import models.db.Tables
import models.db.Tables._
import otw.api.response.{WorkFoundResponse, WorkNotFoundResponse, ArchiveResponse, FindUrlResponse}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio
import slick.dbio.Effect.Read
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object MySqlDatabase {

  val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

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

  lazy val authorsAndStories = Authors
    .joinLeft(storiesAndChapters).on(_.id === _._1.authorid)
    .map { case ((author, storiesWithChapters)) =>
      (author, storiesWithChapters)
    }

  def authorBookmarks(id: Long) = db.run { Bookmarks.filter(_.authorid === id).result }

  private def sequence[T](l: Seq[Option[T]]) =
    if (l.contains(None)) None else Some(l.flatten)

  def authorsWithWorks(filter: String = ""): Future[Seq[AuthorWithWorks]] = db.run {

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

    for {
      authorResults <- authorsAndStories.result

    } yield {
      val stories = groupStoryResults(authorResults)
        stories.map { case (author, stories) =>
          val bookmarkFuture =
            authorBookmarks(author.ID).map(_.map(b => (Bookmark.apply _).tupled(Tables.BookmarksRow
                                                                                                     .unapply(b).get)))
          Await.result(bookmarkFuture.map { bookmarks =>
            AuthorWithWorks(author,
                            stories,
                            if (bookmarks.isEmpty) None else Some(bookmarks))
          }, 10 seconds)
        }.toSeq
        .sortBy(_.author.name)
    }
  }

  def updateStoryStatuses(findResult: FindUrlResponse, stories: Seq[StoryWithChapters]) = {

    def storyQ(id: Long) = Stories.filter(_.id === id).map(x => (x.imported, x.ao3url))

    val resultsWithWorks = findResult.body zip stories

    for {
      r <- resultsWithWorks
    } yield {
      r._1 match {
        case w: WorkFoundResponse => db.run(storyQ(r._2.story.ID).update((true, Some(w.workUrl))))
        case _: WorkNotFoundResponse => db.run(storyQ(r._2.story.ID).update((false, None)))
      }
    }
  }
}
