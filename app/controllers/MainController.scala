package controllers

import otw.api.ArchiveClient
import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter

import scala.concurrent.ExecutionContext.Implicits.global

object MainController extends Controller {

  val config = play.Play.application.configuration

    def index = Action.async {

      val works = ArchiveClient(config.getString("archive.token"), config.getString("archive.host"))

      val thing = works.checkUrls(List("foo"))

      thing.map { msg =>
        Ok(views.html.index.render(
          msg.toString
        ))
      }

    }

  def javascriptRoutes = {
    Action { implicit request =>
      Ok(
        JavaScriptReverseRouter("jsRoutes")(
          routes.javascript.MessageController.getMessage
        )
      ).as("text/javascript")
    }
  }

}
