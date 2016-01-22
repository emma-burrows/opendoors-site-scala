package controllers

import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter

object MainController extends Controller {

  def javascriptRoutes = {
    Action { implicit request =>
      Ok(
        JavaScriptReverseRouter("jsRoutes")(
          routes.javascript.AuthorsController.importAll,
          routes.javascript.AuthorsController.findAll
        )
      ).as("text/javascript")
    }
  }

}
