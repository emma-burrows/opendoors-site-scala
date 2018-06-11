package controllers

import java.nio.charset.Charset

import javax.inject.Inject
import models.db.Tables._
import models.db.Tables.profile.api._
import org.webjars.play.WebJarsUtil
import otw.api.ArchiveClient
import otw.api.request.{FindWorkRequest, OriginalRef}
import otw.api.response.FindWorkResponse
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.InjectedController
import services.Archive
import utils.Json

case class GenericResponse(status: Int, body: String)

class AuthorsController @Inject() (env: play.api.Environment, config: play.api.Configuration, msdb: services.MySqlDatabase,
                                   archiveClient: ArchiveClient)
  extends InjectedController {
  val webJarsUtil: WebJarsUtil = new WebJarsUtil(config, env)

  private val appName = config.getOptional[String]("application.name").getOrElse("opendoors-scala")
  private val archive = if (archiveClient == null) {
    ArchiveClient(config.getOptional[String]("archive.token").getOrElse(""),
                  config.getOptional[String]("archive.host").getOrElse(""))
  }
  else {
    archiveClient
  }

  def authorFuture(authorId: Long) = msdb.authorsWithItems().map { authors =>
    authors.filter(a => a.author.ID == authorId).head
  }

  def list = Action.async { implicit request =>
    for {
      config <- msdb.archiveconfig
      authors <- msdb.authorsWithItems()
    }
    yield {
      authors.groupBy(_.stories)
      Ok(views.html.authors(authors, appName, config, webJarsUtil))
    }
  }

  def findAll(authorId: Long) = Action.async { implicit request =>
    for {
      stories <- authorFuture(authorId).map(_.stories.getOrElse(List()))
      urls     = stories.map(s => OriginalRef(s.story.ID.toString, s.story.url.getOrElse("")))
      result  <- archive.findUrls(FindWorkRequest(urls.toList))
    } yield {
      if (result.isRight) {
        result match {
          case Right(resp) => msdb.updateStoryStatuses(resp.asInstanceOf[FindWorkResponse], stories)
          case Left(e) => println("failed")
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

  def doNotImportAll(authorId: Long, doNotImport: Boolean) = Action.async { implicit request =>

    val authorQ    = for { a <- Authors   if a.id === authorId } yield a.donotimport
    val storiesQ   = for { s <- Stories   if s.authorid === authorId } yield s.donotimport
    val bookmarksQ = for { b <- Bookmarks if b.authorid === authorId } yield b.donotimport

    val authorUpdate   = authorQ.update(doNotImport)
    val storyUpdate    = storiesQ.update(doNotImport)
    val bookmarkUpdate = bookmarksQ.update(doNotImport)

    for {
      author    <- authorFuture(authorId)
      authors   <- msdb.db.run(authorUpdate)
      stories   <- msdb.db.run(storyUpdate)
      bookmarks <- msdb.db.run(bookmarkUpdate)
    } yield {
      Ok(Json.writeJson(GenericResponse(200,
          s"Updated author ${author.author.name} and $stories stories and $bookmarks bookmarks")))
    }
  }
}
