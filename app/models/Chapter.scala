package models

import org.joda.time.DateTime

case class Chapter(ID: Int,
                   title: String,
                   authorid: Int,
                   text: String,
                   date: DateTime,
                   storyid: Int,
                   notes: String,
                   position: Option[Long],
                   url: String,

                   story: Option[Story])
