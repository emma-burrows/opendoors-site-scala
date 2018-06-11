package controllers

import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter

class MainController extends InjectedController {

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes") (
        routes.javascript.AuthorsController.importAll,
        routes.javascript.AuthorsController.findAll,
        routes.javascript.AuthorsController.doNotImportAll,
        routes.javascript.Assets.at
      )
    ).as("text/javascript")
  }
}
