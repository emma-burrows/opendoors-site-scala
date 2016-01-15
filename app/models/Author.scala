package models

case class Author(ID: Long,
                  name: String,
                  email: String = "",
                  imported: Boolean = false,
                  doNotImport: Boolean = false,

                  stories: Option[List[Story]] = None,
                  bookmarks: Option[List[Bookmark]] = None
                 )
