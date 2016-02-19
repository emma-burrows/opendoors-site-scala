package services

import models.{Author, Story}
import otw.api.request.Work
import otw.api.response.{ArchiveApiError, ArchiveResponse}
import utils.Json

object Archive {

  def responseToJson(thing: Either[Throwable, ArchiveResponse]): String = thing match {

    case Right(resp) =>
      println("Resp: " + resp)
      Json.writeJson(resp)

    case Left(ex) =>
      val archiveApiError = ArchiveApiError(500, error = ex.getMessage)
      Json.writeJson(archiveApiError)

  }

  def storyToArchiveItem(author: Author, story: Story) = {
    Work(
      url = story.url.getOrElse(""),
      author = author.name,
      title = "",
      summary = story.summary.getOrElse(""),
      fandomString = story.fandoms.getOrElse(""),
      ratingString = story.rating,
      categoryString = story.categories.getOrElse(""),
      relationshipString = story.relationships.getOrElse(""),
      characterString = story.characters.getOrElse("")
    )
  }
}
