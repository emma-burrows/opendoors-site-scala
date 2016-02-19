package services

import models.db.Tables.profile.api._
import models.{Bookmark, Story, Author, AuthorWithWorks}
import models.db.Tables
import models.db.Tables._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

object MySqlDatabase {

  val db = DatabaseConfigProvider.get[JdbcProfile](Play.current).db

  lazy val authorsAndWorks = Authors
    .joinLeft(Stories).on(_.id === _.authorid)
    .joinLeft(Bookmarks).on(_._1.id === _.authorid)
    .map { case ((a, s), b) => (a, s, b) }

  private def sequence[T](l : List[Option[T]]) =
    if (l.contains(None)) None else Some(l.flatten)

  def authorsWithWorks(filter: String = ""): Future[Seq[AuthorWithWorks]] = db.run {

    authorsAndWorks.result.map(
      _.groupBy {
          case (a, s, b) =>
            (Author.apply _).tupled(Tables.AuthorsRow.unapply(a).get)
      }
        .map {
          case (author, works) =>
            (
              author,
              works.map { case (_, s, b) =>
                val stories   = s.map(x => (Story.apply _).tupled(Tables.StoriesRow.unapply(x).get))
                val bookmarks = b.map(x => (Bookmark.apply _).tupled(Tables.BookmarksRow.unapply(x).get))
                (stories, bookmarks)
              }
              .toList
              .unzip
            )
        }
        .map { case (author, (stories, bookmarks)) =>
          AuthorWithWorks(author, sequence(stories), sequence(bookmarks))
        }
        .toSeq
        .sortBy(aww => aww.author.name)
    )

  }

}
