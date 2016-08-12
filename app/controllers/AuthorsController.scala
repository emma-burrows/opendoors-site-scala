package controllers

import java.nio.charset.Charset

import models.db.Tables._
import models.db.Tables.profile.api._
import otw.api.ArchiveClient
import otw.api.request.{FindWorkRequest, OriginalRef}
import otw.api.response.{FindWorkResponse, Error}
import play.api.Play
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import services.MySqlDatabase._
import services.{Archive, ThingGenerator}
import utils.Json

import scala.concurrent.Await
import scala.concurrent.duration._

case class GenericResponse(status: Int, body: String)

object AuthorsController extends Controller with ThingGenerator {

  val playConfig = Play.application.configuration
  val config = Await.result(archiveconfig, 1 second)

  val appName = playConfig.getString("application.name").getOrElse("")

  val archive = ArchiveClient(config.archivetoken.getOrElse(""), config.archivehost.getOrElse(""))

  def authorFuture(authorId: Long) = authorsWithItems().map { authors =>
    authors.filter(a => a.author.ID == authorId).head
  }

  def list = Action.async { request =>
    authorsWithItems().map { authors =>
      authors.groupBy(_.stories)
      Ok(views.html.authors(authors, appName, config))
    }
  }

  def findAll(authorId: Long) = Action.async {
    for {
      stories <- authorFuture(authorId).map(_.stories.getOrElse(List()))
      urls     = stories.map(s => OriginalRef(s.story.ID.toString, s.story.url.getOrElse("")))
      result  <- archive.findUrls(FindWorkRequest(urls.toList))
    } yield {
      if (result.isRight) {
        result match {
          case Right(resp) => updateStoryStatuses(resp.asInstanceOf[FindWorkResponse], stories)
          case Left(e) =>
        }
      }

      val jsonResponse = Archive.responseToJson(result)

      println("FindAll response: " + jsonResponse)
      Ok(jsonResponse)
    }
  }

  def importAll(authorId: Long) = Action.async { implicit request =>
    for {
      author <- authorFuture(authorId)
      items   = author.stories.map(list => list.map(Archive.storyToArchiveItem(author.author, _))).getOrElse(List())
      result <- archive.createWorks("testy", sendClaimEmails = false, postWithoutPreview = false, Charset.defaultCharset(), "", items.toList)
    } yield {
      println("ImportAll response: " + result)
      Ok(Json.writeJson(result))
    }
  }

  def doNotImportAll(authorId: Long, doNotImport: Boolean) = Action.async {

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
