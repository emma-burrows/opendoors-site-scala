package models.db

import slick.jdbc._
import slick.lifted.TableQuery

trait ArchiveConfigTable {

  self: Tables =>

  /** Collection-like TableQuery object for table ArchiveConfig */
  lazy val ArchiveConfigs = new TableQuery(tag => new ArchiveConfigs(tag))

  val profile: slick.driver.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.jdbc.{GetResult => GR}

  /** GetResult implicit for fetching ArchiveConfigRow objects using plain SQL queries */
  implicit def GetResultArchiveconfigRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]],
    e3: GR[Boolean]): GR[ArchiveConfigRow] = GR {
    prs => import prs._
      ArchiveConfigRow
        .tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean],
                  <<[Boolean], <<[Int], <<[String], <<[String], <<[Boolean], <<[Boolean]))
  }

  // Entity class storing rows of table ArchiveConfig
  case class ArchiveConfigRow(id: Int,
    key: String,
    name: Option[String] = None,
    fandom: Option[String] = None,
    odnote: Option[String] = None,
    archivehost: Option[String] = Some("ariana.ao3.org"),
    archivetoken: Option[String] = None,
    sendemail: Boolean = false,
    postworks: Boolean = false,
    itemsperpage: Int = 30,
    archivist: String,
    collectionname: String,
    imported: Boolean = false,
    notimported: Boolean = false)

  /** Table description of table archiveconfig. Objects of this class serve as prototypes for rows in queries. */
  class ArchiveConfigs(_tableTag: Tag) extends Table[ArchiveConfigRow](_tableTag, "archiveconfig") {
    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column Key SqlType(VARCHAR), Length(45,true) */
    val key: Rep[String] = column[String]("Key", O.Length(45, varying = true))
    /** Database column Name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("Name", O.Length(255, varying = true), O.Default(None))
    /** Database column Fandom SqlType(VARCHAR), Length(255,true), Default(None) */
    val fandom: Rep[Option[String]] = column[Option[String]]("Fandom", O.Length(255, varying = true), O.Default(None))
    /** Database column ODNote SqlType(TEXT), Default(None) */
    val odnote: Rep[Option[String]] = column[Option[String]]("ODNote", O.Default(None))
    /** Database column ArchiveHost SqlType(VARCHAR), Length(255,true), Default(Some(ariana.ao3.org)) */
    val archivehost: Rep[Option[String]] = column[Option[String]]("ArchiveHost", O.Length(255, varying = true), O
      .Default(Some("ariana.ao3.org")))
    /** Database column ArchiveToken SqlType(VARCHAR), Length(255,true), Default(None) */
    val archivetoken: Rep[Option[String]] = column[Option[String]]("ArchiveToken", O.Length(255, varying = true), O
      .Default(None))
    /** Database column SendEmail SqlType(BIT), Default(false) */
    val sendemail: Rep[Boolean] = column[Boolean]("SendEmail", O.Default(false))
    /** Database column PostWorks SqlType(BIT), Default(false) */
    val postworks: Rep[Boolean] = column[Boolean]("PostWorks", O.Default(false))
    /** Database column ItemsPerPage SqlType(INT), Default(30) */
    val itemsperpage: Rep[Int] = column[Int]("ItemsPerPage", O.Default(30))
    /** Database column Archivist SqlType(VARCHAR), Length(100,true) */
    val archivist: Rep[String] = column[String]("Archivist", O.Length(100, varying = true))
    /** Database column CollectionName SqlType(VARCHAR), Length(255,true) */
    val collectionname: Rep[String] = column[String]("CollectionName", O.Length(255, varying = true))
    /** Database column Imported SqlType(BIT), Default(false) */
    val imported: Rep[Boolean] = column[Boolean]("Imported", O.Default(false))
    /** Database column NotImported SqlType(BIT), Default(false) */
    val notimported: Rep[Boolean] = column[Boolean]("NotImported", O.Default(false))
    /** Uniqueness Index over (key) (database name Key_UNIQUE) */
    val index1 = index("Key_UNIQUE", key, unique = true)

    def * = (id, key, name, fandom, odnote, archivehost, archivetoken, sendemail, postworks, itemsperpage, archivist,
      collectionname, imported, notimported) <>(ArchiveConfigRow
      .tupled, ArchiveConfigRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(key), name, fandom, odnote, archivehost, archivetoken, Rep.Some(sendemail), Rep
      .Some(postworks), Rep.Some(itemsperpage), Rep.Some(archivist), Rep.Some(collectionname), Rep.Some(imported), Rep
      .Some(notimported)).shaped.<>({ r => import r._;
      _1.map(_ => ArchiveConfigRow.tupled((_1.get, _2
        .get, _3, _4, _5, _6, _7, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get)))
    }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }

}
