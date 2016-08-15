package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Controller, Action}


class ChapterController @Inject() (config: play.api.Configuration, msdb: services.MySqlDatabase)
  extends Controller {

  val appName = config.getString("application.name").getOrElse("opendoors")

  def show(chapterId: Long) = Action.async { implicit request =>
    for {
      config <- msdb.archiveconfig
      chapterWithStory <- msdb.chapterWithStory(chapterId) }
    yield
      Ok(views.html.chapter(chapterWithStory, appName, config))
  }
}
