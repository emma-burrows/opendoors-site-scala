package controllers

import models.db.Tables
import models.db.Tables._
import models.db.Tables.profile.api._
import models.{Author, AuthorWithWorks, Bookmark, Story}
import otw.api.ArchiveClient
import otw.api.response.Error
import play.api.Play
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play.current
import play.api.mvc.{Action, Controller}
import services.{Archive, ThingGenerator}
import utils.Json

import scala.concurrent.Future

case class GenericResponse(status: Int, body: String)

object AuthorsController extends Controller with ThingGenerator {

  val config = Play.application.configuration

  val db = Database.forURL(url = config.getString("slick.dbs.default.db.url").getOrElse(""),
                           user = config.getString("slick.dbs.default.db.user").getOrElse(""),
                           password = config.getString("slick.dbs.default.db.password").getOrElse(""),
                           driver = config.getString("slick.dbs.default.db.driver").getOrElse(""))

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
      Ok(views.html.authors(authors, config.getString("application.name").getOrElse("")))
    }
  }

  def findAll(authorId: Long) = Action.async {
    val works = ArchiveClient(config.getString("archive.token").getOrElse(""),
                              config.getString("archive.host").getOrElse(""))
    val author = authorsWithWorks.map { authors =>
      authors.filter(a => a.author.ID == authorId).head
    }

    for {
      stories <- author.map(_.stories.getOrElse(List()))
      urls     = stories.map(_.url.getOrElse(""))
      result  <- works.findUrls(urls)
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

  def doNotImportAll(authorId: Long, doNotImport: Boolean) = Action.async {

    println("do not import: " + doNotImport)
    val authorFuture = authorsWithWorks.map { authors =>
      authors.filter(a => a.author.ID == authorId).head
    }

    // Update author
    val authorQ    = for { a <- Authors   if a.id === authorId } yield a.donotimport
    val storiesQ   = for { s <- Stories   if s.authorid === authorId } yield s.donotimport
    val bookmarksQ = for { b <- Bookmarks if b.authorid === authorId } yield b.donotimport

    val authorUpdate   = authorQ.update(doNotImport)
    val storyUpdate    = storiesQ.update(doNotImport)
    val bookmarkUpdate = bookmarksQ.update(doNotImport)

    for {
      author    <- authorFuture
      authors   <- db.run(authorUpdate)
      stories   <- db.run(storyUpdate)
      bookmarks <- db.run(bookmarkUpdate)
    } yield {
      Ok(Json.writeJson(GenericResponse(200,
          s"Updated author ${author.author.name} and $stories stories and $bookmarks bookmarks")))
    }
  }
}
