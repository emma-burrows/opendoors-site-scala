package services

import models.db.Tables.profile.api._
import models._
import models.db.Tables
import models.db.Tables._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.collection.immutable.Iterable
import scala.concurrent.Future

object MySqlDatabase {

  val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

  lazy val storiesAndChapters = Stories.joinLeft(Chapters).on(_.id === _.storyid)

  lazy val authorsAndStories = Authors
    .joinLeft(storiesAndChapters).on(_.id === _._1.authorid)
    .map { case ((author, storiesWithChapters)) =>
      (author, storiesWithChapters)
    }

  lazy val authorsAndBookmarks = Authors
    .joinLeft(Bookmarks).on(_.id === _.authorid)

  private def sequence[T](l : Seq[Option[T]]) =
    if (l.contains(None)) None else Some(l.flatten)

  def authorsWithWorks(filter: String = ""): Future[Seq[AuthorWithWorks]] = db.run {

    for {
      authorResults <- authorsAndStories.result
    } yield {
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

          AuthorWithWorks(author, stories)
        }
        .toSeq
        .sortBy(aww => aww.author.name)
    }
  }
}
