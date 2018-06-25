package otw.api.utils

import java.nio.charset.Charset

import dispatch.Defaults._
import dispatch._

// Abstraction on top of Dispatch in case the latter gets replaced in future
private[api] class ArchiveHttp(archiveToken: String, archiveApiHost: String) {

  def get(urlPath: String) = {
    val request =
      url(s"http://$archiveApiHost/$urlPath")
        .setContentType("application/json", Charset.forName("utf-8"))
        .setHeader("Authorization", s"Token token=$archiveToken")
        .GET

    Http.withConfiguration(_.setFollowRedirect(true)){
      request > (x => HttpStatusWithJsonBody(x.getStatusCode, as.json4s.Json(x)))
    }.either
  }

  def post(urlPath: String, jsonBody: String): Future[Either[Throwable, HttpStatusWithJsonBody]] = {
    val request =
      url(s"http://$archiveApiHost/$urlPath")
        .setContentType("application/json", Charset.forName("utf-8"))
        .setHeader("Authorization", s"Token token=$archiveToken")
        .setBody(jsonBody)
        .POST

    Http.withConfiguration(_.setFollowRedirect(true)) {
      request > (x => HttpStatusWithJsonBody(x.getStatusCode, as.json4s.Json(x)))
    }.either
  }

}
