package controllers

import controllers.MessageController._
import otw.api.ArchiveClient
import play.api._
import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter
import scala.concurrent.ExecutionContext.Implicits.global


import scala.util.{Success, Failure}

object MainController extends Controller {

    def index = Action.async {

      val tokens = Map(
        "ariana" -> ("ariana.ao3.org/otw.api/v1", "61a87058c13573859634acf273e11c63"),
        "mac" -> ("localhost:3000", "e1b6298a6209dd65e5df95b83b10c0f1")
      )

      val works = ArchiveClient(tokens("mac")._2, tokens("mac")._1)

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
