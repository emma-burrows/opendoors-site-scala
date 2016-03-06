package controllers

import java.nio.charset.Charset

import models.db.Tables._
import models.db.Tables.profile.api._
import otw.api.ArchiveClient
import otw.api.response.Error
import play.api.Play
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import services.MySqlDatabase._
import services.{Archive, ThingGenerator}
import utils.Json

case class GenericResponse(status: Int, body: String)

object AuthorsController extends Controller with ThingGenerator {

  val config = Play.application.configuration

  val appName = config.getString("application.name").getOrElse("")

  val archive = ArchiveClient(config.getString("archive.token").getOrElse(""),
                            config.getString("archive.host").getOrElse(""))

  def authorFuture(authorId: Long) = authorsWithWorks().map { authors =>
    authors.filter(a => a.author.ID == authorId).head
  }

  def list = Action.async { request =>
    authorsWithWorks().map { authors =>
      Ok(views.html.authors(authors, appName))
    }
  }

  def findAll(authorId: Long) = Action.async {
    for {
      stories <- authorFuture(authorId).map(_.stories.getOrElse(List()))
      urls     = stories.map(_.story.url.getOrElse(""))
      result  <- archive.findUrls(urls)
    } yield {
      println("FindAll response: " + Archive.responseToJson(result))
      Ok(Archive.responseToJson(result))
    }
  }

  def importAll(authorId: Long) = Action.async {
    for {
      author <- authorFuture(authorId)
      items   = author.stories.map(list => list.map(Archive.storyToArchiveItem(author.author, _))).getOrElse(List())
      result <- archive.createWorks("testy", false, false, Charset.defaultCharset(), "", items)
    } yield {
      println(result)
      Ok(Json.writeJson(result))
    }
  }

  def doNotImportAll(authorId: Long, doNotImport: Boolean) = Action.async {

    // Update author
    val authorQ    = for { a <- Authors   if a.id === authorId } yield a.donotimport
    val storiesQ   = for { s <- Stories   if s.authorid === authorId } yield s.donotimport
    val bookmarksQ = for { b <- Bookmarks if b.authorid === authorId } yield b.donotimport

    val authorUpdate   = authorQ.update(doNotImport)
    val storyUpdate    = storiesQ.update(doNotImport)
    val bookmarkUpdate = bookmarksQ.update(doNotImport)

    for {
      author    <- authorFuture(authorId)
      authors   <- db.run(authorUpdate)
      stories   <- db.run(storyUpdate)
      bookmarks <- db.run(bookmarkUpdate)
    } yield {
      Ok(Json.writeJson(GenericResponse(200,
          s"Updated author ${author.author.name} and $stories stories and $bookmarks bookmarks")))
    }
  }
}
