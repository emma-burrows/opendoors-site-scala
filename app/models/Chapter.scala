package models

import java.util.Date

case class Chapter(ID: Long,
                   position: Option[Long] = None,
                   title: String,
                   authorid: Int,
                   text: Option[String] = None,
                   date: Option[Date],
                   storyid: Long,
                   notes: Option[String] = None,
                   url: Option[String] = None)

case class ChapterWithStory(
                           chapter: Chapter,
                           storyWithAuthors: StoryWithAuthors
                           )
