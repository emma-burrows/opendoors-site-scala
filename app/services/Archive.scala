package services

import models.{Chapter, StoryWithChapters, Author, Story}
import otw.api.request.Work
import otw.api.response.{ArchiveApiError, ArchiveResponse}
import play.api.mvc.{AnyContent, Request}
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

  def storyToArchiveItem(author: Author, storyWithChapters: StoryWithChapters)(implicit request: Request[AnyContent]) = {
    val story = storyWithChapters.story
    val chapters =
      storyWithChapters.chapters.map {
        list => list.map(chapter => chapterUrl(chapter))
      }.getOrElse(List())

    Work(
      id = story.ID.toString,
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
      chapterUrls = chapters
    )
  }

  def chapterUrl(chapter: Chapter)(implicit request: Request[AnyContent]) =
    chapter.url match {
      case Some(url) => if (url.startsWith("http")) url
        else controllers.routes.Assets.at("works/" + chapter.url.get).absoluteURL().replaceAll("/assets", "")

      case None => "" // TODO: some kind of chapter controller
    }
}
