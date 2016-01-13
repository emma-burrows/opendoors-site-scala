package controllers

import otw.api.ArchiveClient
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.routing.JavaScriptReverseRouter

import scala.util.Properties

case class Message(value: String)

object MessageController extends Controller {

  implicit val fooWrites = Json.writes[Message]

  def getMessage = {
    Action.async {
      val archive_token = Properties.propOrElse("archiveToken", "1234")
      val archive_api_url = Properties.propOrElse("archiveApiHost", "archiveofourown.org")

      val works = ArchiveClient(archive_token, archive_api_url)

      val thing = works.checkUrls(List("bar"))
      thing.map { msg =>
        Ok(Json.toJson(msg))
      }
    }
  }

}
