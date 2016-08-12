package controllers

import controllers.AuthorsController._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Controller, Action}
import services.MySqlDatabase._


object ChapterController extends Controller {

  def show(chapterId: Long) = Action.async { request =>
    for { chapterWithStory <- chapterWithStory(chapterId) }
    yield
      Ok(views.html.chapter(chapterWithStory, appName, config))
  }
}
