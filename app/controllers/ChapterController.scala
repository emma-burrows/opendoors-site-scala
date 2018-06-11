package controllers

import javax.inject.Inject
import org.webjars.play.WebJarsUtil
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.InjectedController


class ChapterController @Inject() (env: play.api.Environment, config: play.api.Configuration, msdb: services.MySqlDatabase)
  extends InjectedController {

  val appName = config.getOptional[String]("application.name").getOrElse("opendoors-scala")

  val webJarsUtil: WebJarsUtil = new WebJarsUtil(config, env)

  def show(chapterId: Long) = Action.async { implicit request =>
    for {
      config <- msdb.archiveconfig
      chapterWithStory <- msdb.chapterWithStory(chapterId) }
    yield
      Ok(views.html.chapter(chapterWithStory, appName, config, webJarsUtil))
  }
}
