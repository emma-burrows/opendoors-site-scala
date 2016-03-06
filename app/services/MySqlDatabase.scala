package services

import models.db.Tables.profile.api._
import models._
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
    .joinLeft(Stories.joinLeft(Chapters).on(_.id === _.storyid)).on(_.id === _._1.authorid)
    .joinLeft(Bookmarks).on(_._1.id === _.authorid)
    .map { case ((author, storiesAndChapters), bookmarks) =>
      (author, storiesAndChapters, bookmarks) }

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
                val stories = s.groupBy(_._1)
                  .map {
                    case (ss, scs: Iterable[(Tables.StoriesRow, Option[Tables.ChaptersRow])]) =>
                      val story = (Story.apply _).tupled(Tables.StoriesRow.unapply(ss).get)
                      val chapters =
                        scs.map(_._2)
                          .map(opt => opt.map((x: Tables.ChaptersRow) => (Chapter.apply _).tupled(Tables.ChaptersRow.unapply(x).get)))
                          .toList
                      StoryWithChapters(story = story, chapters = sequence(chapters))
                  }
                val bookmarks = b.map(x => (Bookmark.apply _).tupled(Tables.BookmarksRow.unapply(x).get))
                (stories, bookmarks)
              }
              .unzip
            )
        }
        .map { case (author, (stories, bookmarks)) =>
          AuthorWithWorks(author,
                          if (stories.isEmpty) None else Some(stories.flatten.toList),
                          sequence(bookmarks.toList))
        }
        .toSeq
        .sortBy(aww => aww.author.name)
    )

  }

}
