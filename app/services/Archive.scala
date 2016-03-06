package services

import models.{StoryWithChapters, Author, Story}
import otw.api.request.Work
import otw.api.response.{ArchiveApiError, ArchiveResponse}
import utils.Json

object Archive {

  def responseToJson(thing: Either[Throwable, ArchiveResponse]): String = thing match {

    case Right(resp) =>
      println("Resp: " + resp)
      Json.writeJson(resp)

    case Left(ex) =>
      val archiveApiError = ArchiveApiError(500, List(ex.getMessage))
      Json.writeJson(archiveApiError)

  }

  def storyToArchiveItem(author: Author, storyWithChapters: StoryWithChapters) = {
    val story = storyWithChapters.story
    val chapters =
      storyWithChapters.chapters.map {
        list => list.map(chapter => chapter.url.getOrElse(""))
      }.getOrElse(List())

    Work(
      url = story.url.getOrElse(""),
      author = author.name,
      email = author.email,
      title = "",
      summary = story.summary.getOrElse(""),
      fandomString = story.fandoms.getOrElse(""),
      ratingString = story.rating,
      categoryString = story.categories.getOrElse(""),
      relationshipString = story.relationships.getOrElse(""),
      characterString = story.characters.getOrElse(""),
      chapter_urls = chapters
    )
  }
}
