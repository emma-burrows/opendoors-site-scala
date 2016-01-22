package controllers

import otw.api.ArchiveClient
import otw.api.response.Error
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import services.{Archive, ThingGenerator}
import utils.Json

import scala.concurrent.Future

object AuthorsController extends Controller with ThingGenerator {

  val config = play.Play.application.configuration

  def list = {
    Action { request =>
      Ok(views.html.authors(generatedAuthors))
    }
  }

  def findAll(authorId: Long) = {
    Action.async {
      Thread.sleep((new util.Random).nextInt(2000))
      val works = ArchiveClient(config.getString("archive.token"), config.getString("archive.host"))
      val author = generatedAuthors.find(a => a.ID == authorId).head
      val storyUrls = author.stories.getOrElse(List()).map(_.url.getOrElse(""))

      val storiesFuture = works.findUrls(storyUrls)

      storiesFuture.map { msg =>
        println("FindAll response: " + Archive.responseToJson(msg))
        Ok(Archive.responseToJson(msg))
      }
    }
  }

  def importAll(authorId: Long) = {
    Action.async {
      Future {
        Ok(Json.writeJson(Error("Not implemented")))
      }
    }
  }
}
