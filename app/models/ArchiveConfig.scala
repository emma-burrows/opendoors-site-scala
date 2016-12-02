package models

case class ArchiveConfig(
  id: Int = 0,
  key: String = "od",
  name: Option[String] = Some("opendoors"),
  fandom: Option[String] = None,
  odnote: Option[String] = None,
  archivehost: Option[String] = Some("ariana.ao3.org"),
  archivetoken: Option[String] = None,
  sendemail: Boolean = false,
  postworks: Boolean = false,
  itemsperpage: Int = 30,
  archivist: String = "testy",
  collectionname: String = "OpenDoors",
  imported: Boolean = false,
  notimported: Boolean = false)
