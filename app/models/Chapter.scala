package models

import java.util.Date

case class Chapter(ID: Long,
                   position: Option[Long],
                   title: String,
                   authorid: Int,
                   text: Option[String] = None,
                   date: Date,
                   storyid: Long,
                   notes: Option[String] = None,
                   url: Option[String] = None)
