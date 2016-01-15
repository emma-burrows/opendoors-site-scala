package controllers

import otw.api.ArchiveClient
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.routing.JavaScriptReverseRouter
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.Properties

case class Message(value: String)

object MessageController extends Controller {

  implicit val fooWrites = Json.writes[Message]
  val config = play.Play.application.configuration

  def getMessage(id: Int = 26) = {
    Action.async {
      val works = ArchiveClient(config.getString("archive.token"), config.getString("archive.host"))

      val url = id match {
        case 1 => "http://astele.co.uk/other/ao3.html"
        case 2 => "bar"
        case 3 => ""
      }
      val thing = works.checkUrlsJson(List(url))
      thing.map { msg =>
        Ok(Json.toJson(msg))
      }
    }
  }

}
