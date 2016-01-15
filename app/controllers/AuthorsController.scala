package controllers

import controllers.MessageController._
import models.Author
import otw.api.ArchiveClient
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object AuthorsController extends Controller {

  lazy val authors = List.tabulate(5)( x =>
    Author(x, s"author$x", s"author$x@example.com")
  )

  def list = Action { request =>
    Ok(views.html.authors(authors))
  }

  def importAll(authorId: Long) = Action.async {
    Thread.sleep((new util.Random).nextInt(2000))
    val works = ArchiveClient(config.getString("archive.token"), config.getString("archive.host"))

    val url = authorId match {
      case 1 => "http://astele.co.uk/other/ao3.html"
      case x if x % 2 == 0 => "bar"
      case x if x % 3 == 0 => ""
      case _ => "foo"
    }
    val thing = works.checkUrlsJson(List(url))

    thing.map { msg =>
      Ok(Json.toJson(msg))
    }
  }
}
