package models

import org.joda.time.DateTime

case class Bookmark(ID: Option[Long],
                    title: String,
                    summary: String,
                    authorID: Long,
                    rating: String,
                    date: DateTime,
                    tags: String,
                    warnings: String,
                    notes: String,
                    url: String,
                    imported: Boolean,
                    doNotImport: Boolean,
                    ao3Url: String,
                    fandoms: String,
                    characters: String,
                    relationships: String,

                    author: Option[Author])
