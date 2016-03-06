package models

import java.sql.Date

import org.joda.time.DateTime

sealed trait Work {
  val ID: Long
  val title: String
  val summary: Option[String] = None
  val notes: Option[String] = None
  val authorID: Long
  val rating: String
  val date: Date
  val tags: Option[String] = None
  val categories: Option[String] = None
  val warnings: Option[String] = None
  val fandoms: Option[String] = None
  val characters: Option[String] = None
  val relationships: Option[String] = None
  val url: Option[String] = None
  val imported: Boolean = false
  val doNotImport: Boolean = false
  val ao3Url: Option[String] = None
}

case class Bookmark(
                     val ID: Long,
                     override val title: String,
                     override val summary: Option[String] = None,
                     override val notes: Option[String] = None,
                     override val authorID: Long,
                     override val rating: String,
                     override val date: Date,
                     override val tags: Option[String] = None,
                     override val categories: Option[String] = None,
                     override val warnings: Option[String] = None,
                     override val fandoms: Option[String] = None,
                     override val characters: Option[String] = None,
                     override val relationships: Option[String] = None,
                     override val url: Option[String],
                     override val imported: Boolean = false,
                     override val doNotImport: Boolean = false,
                     override val ao3Url: Option[String] = None
                   ) extends Work

case class Story(
                  val ID: Long,
                  override val title: String,
                  override val summary: Option[String] = None,
                  override val notes: Option[String] = None,
                  override val authorID: Long,
                  override val rating: String,
                  override val date: Date,
                  override val tags: Option[String] = None,
                  override val categories: Option[String] = None,
                  override val warnings: Option[String] = None,
                  override val fandoms: Option[String] = None,
                  override val characters: Option[String] = None,
                  override val relationships: Option[String] = None,
                  override val url: Option[String] = None,
                  override val imported: Boolean = false,
                  override val doNotImport: Boolean = false,
                  override val ao3Url: Option[String] = None
                ) extends Work

case class StoryWithChapters(
                            story: Story,
                            chapters: Option[List[Chapter]] = None
                            )
