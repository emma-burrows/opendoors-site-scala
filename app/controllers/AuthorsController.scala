package controllers

import models.db.Tables
import models.db.Tables._
import models.db.Tables.profile.api._
import models.{Author, AuthorWithWorks, Bookmark, Story}
import otw.api.ArchiveClient
import otw.api.response.Error
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import services.{Archive, ThingGenerator}
import utils.Json

import scala.concurrent.Future

object AuthorsController extends Controller with ThingGenerator {

  val config = play.Play.application.configuration

  val db = Database.forURL(config.getString("slick.dbs.default.db.url"),
                           user = config.getString("slick.dbs.default.db.user"),
                           password = config.getString("slick.dbs.default.db.password"),
                           driver = config.getString("slick.dbs.default.db.driver"))

  lazy val authorsAndWorks = Authors
    .joinLeft(Stories).on(_.id === _.authorid)
    .joinLeft(Bookmarks).on(_._1.id === _.authorid)
    .map { case ((a, s), b) => (a, s, b) }

  def sequence[T](l : List[Option[T]]) =
    if (l.contains(None)) None else Some(l.flatten)

  def authorsWithWorks: Future[Seq[AuthorWithWorks]] = db.run {

    authorsAndWorks.result.map(
      _.groupBy { case (a, s, b) => (Author.apply _).tupled(Tables.AuthorsRow.unapply(a).get) }
        .map {
          case (author, works) =>
            (author,
              works.map { case (_, s, b) =>
                val stories = s.map(x => (Story.apply _).tupled(Tables.StoriesRow.unapply(x).get))
                val bookmarks = b.map(x => (Bookmark.apply _).tupled(Tables.BookmarksRow.unapply(x).get))
                (stories, bookmarks)
              }.toList.unzip
              )
        }
        .map { case (author, (stories, bookmarks)) =>
          AuthorWithWorks(author, sequence(stories), sequence(bookmarks))
        }.toSeq
    )
  }

  def list = Action.async { request =>


    authorsWithWorks.map { authors =>
      Ok(views.html.authors(authors, config.getString("application.name")))
    }
  }

  def findAll(authorId: Long) = Action.async {
    val works = ArchiveClient(config.getString("archive.token"), config.getString("archive.host"))
    val author = authorsWithWorks.map { authors =>
      authors.filter(a => a.author.ID == authorId).head
    }

    for {
      urls <- author.map(_.stories.getOrElse(List()).map(_.url.getOrElse("")))
      result <- works.findUrls(urls)
    } yield {
      println("FindAll response: " + Archive.responseToJson(result))
      Ok(Archive.responseToJson(result))
    }
  }

  def importAll(authorId: Long) = Action.async {
    Future {
      Ok(Json.writeJson(Error("Not implemented")))
    }
  }

  def doNotImportAll(authorId: Long) = Action.async {
    Future {
      Ok(Json.writeJson(Error("Not implemented")))
    }
  }
}
