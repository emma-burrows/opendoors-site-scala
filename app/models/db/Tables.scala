package models.db

import java.util.Date

import org.joda.time.DateTime

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables extends BookmarkTable with StoriesTable {
  val profile: slick.driver.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction

  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Archiveconfig.schema, Authors.schema, Bookmarks.schema, Chapters.schema, PlayEvolutions.schema, Stories.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Archiveconfig
    *
    *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param key Database column Key SqlType(VARCHAR), Length(45,true)
   *  @param name Database column Name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param fandom Database column Fandom SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param odnote Database column ODNote SqlType(TEXT), Default(None)
   *  @param archivehost Database column ArchiveHost SqlType(VARCHAR), Length(255,true), Default(Some(ariana.ao3.org))
   *  @param archivetoken Database column ArchiveToken SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param sendemail Database column SendEmail SqlType(BIT), Default(false)
   *  @param postworks Database column PostWorks SqlType(BIT), Default(false)
   *  @param itemsperpage Database column ItemsPerPage SqlType(INT), Default(30)
   *  @param archivist Database column Archivist SqlType(VARCHAR), Length(100,true)
   *  @param collectionname Database column CollectionName SqlType(VARCHAR), Length(255,true)
   *  @param imported Database column Imported SqlType(INT), Default(0)
   *  @param notimported Database column NotImported SqlType(INT), Default(0) */
  case class ArchiveconfigRow(id: Int, key: String, name: Option[String] = None, fandom: Option[String] = None, odnote: Option[String] = None, archivehost: Option[String] = Some("ariana.ao3.org"), archivetoken: Option[String] = None, sendemail: Boolean = false, postworks: Boolean = false, itemsperpage: Int = 30, archivist: String, collectionname: String, imported: Boolean = false, notimported: Boolean = false)
  /** GetResult implicit for fetching ArchiveconfigRow objects using plain SQL queries */
  implicit def GetResultArchiveconfigRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Boolean]): GR[ArchiveconfigRow] = GR{
    prs => import prs._
    ArchiveconfigRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean], <<[Boolean], <<[Int], <<[String], <<[String], <<[Boolean], <<[Boolean]))
  }
  /** Table description of table archiveconfig. Objects of this class serve as prototypes for rows in queries. */
  class Archiveconfig(_tableTag: Tag) extends Table[ArchiveconfigRow](_tableTag, "archiveconfig") {
    def * = (id, key, name, fandom, odnote, archivehost, archivetoken, sendemail, postworks, itemsperpage, archivist, collectionname, imported, notimported) <> (ArchiveconfigRow.tupled, ArchiveconfigRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(key), name, fandom, odnote, archivehost, archivetoken, Rep.Some(sendemail), Rep.Some(postworks), Rep.Some(itemsperpage), Rep.Some(archivist), Rep.Some(collectionname), Rep.Some(imported), Rep.Some(notimported)).shaped.<>({r=>import r._; _1.map(_=> ArchiveconfigRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column Key SqlType(VARCHAR), Length(45,true) */
    val key: Rep[String] = column[String]("Key", O.Length(45,varying=true))
    /** Database column Name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("Name", O.Length(255,varying=true), O.Default(None))
    /** Database column Fandom SqlType(VARCHAR), Length(255,true), Default(None) */
    val fandom: Rep[Option[String]] = column[Option[String]]("Fandom", O.Length(255,varying=true), O.Default(None))
    /** Database column ODNote SqlType(TEXT), Default(None) */
    val odnote: Rep[Option[String]] = column[Option[String]]("ODNote", O.Default(None))
    /** Database column ArchiveHost SqlType(VARCHAR), Length(255,true), Default(Some(ariana.ao3.org)) */
    val archivehost: Rep[Option[String]] = column[Option[String]]("ArchiveHost", O.Length(255,varying=true), O.Default(Some("ariana.ao3.org")))
    /** Database column ArchiveToken SqlType(VARCHAR), Length(255,true), Default(None) */
    val archivetoken: Rep[Option[String]] = column[Option[String]]("ArchiveToken", O.Length(255,varying=true), O.Default(None))
    /** Database column SendEmail SqlType(BIT), Default(false) */
    val sendemail: Rep[Boolean] = column[Boolean]("SendEmail", O.Default(false))
    /** Database column PostWorks SqlType(BIT), Default(false) */
    val postworks: Rep[Boolean] = column[Boolean]("PostWorks", O.Default(false))
    /** Database column ItemsPerPage SqlType(INT), Default(30) */
    val itemsperpage: Rep[Int] = column[Int]("ItemsPerPage", O.Default(30))
    /** Database column Archivist SqlType(VARCHAR), Length(100,true) */
    val archivist: Rep[String] = column[String]("Archivist", O.Length(100,varying=true))
    /** Database column CollectionName SqlType(VARCHAR), Length(255,true) */
    val collectionname: Rep[String] = column[String]("CollectionName", O.Length(255,varying=true))
    /** Database column Imported SqlType(BIT), Default(false) */
    val imported: Rep[Boolean] = column[Boolean]("Imported", O.Default(false))
    /** Database column NotImported SqlType(BIT), Default(false) */
    val notimported: Rep[Boolean] = column[Boolean]("NotImported", O.Default(false))

    /** Uniqueness Index over (key) (database name Key_UNIQUE) */
    val index1 = index("Key_UNIQUE", key, unique=true)
  }
  /** Collection-like TableQuery object for table Archiveconfig */
  lazy val Archiveconfig = new TableQuery(tag => new Archiveconfig(tag))

  /** Entity class storing rows of table Authors
    *
    * @param id Database column id SqlType(INT), AutoInc, PrimaryKey
    * @param name Database column name SqlType(VARCHAR), Length(255,true), Default()
    * @param email Database column email SqlType(VARCHAR), Length(255,true), Default()
    * @param imported Database column imported SqlType(BIT), Default(false)
    * @param donotimport Database column doNotImport SqlType(BIT), Default(false) */
  case class AuthorsRow(id: Long, name: String = "", email: String = "", imported: Boolean = false,
                        donotimport: Boolean = false)

  /** GetResult implicit for fetching AuthorsRow objects using plain SQL queries */
  implicit def GetResultAuthorsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Boolean]): GR[AuthorsRow] = {
    GR {
      prs => import prs._
        AuthorsRow.tupled((<<[Long], <<[String], <<[String], <<[Boolean], <<[Boolean]))
    }
  }

  /** Table description of table authors. Objects of this class serve as prototypes for rows in queries. */
  class Authors(_tableTag: Tag) extends Table[AuthorsRow](_tableTag, "authors") {

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(255, varying = true), O.Default(""))
    /** Database column email SqlType(VARCHAR), Length(255,true), Default() */
    val email: Rep[String] = column[String]("email", O.Length(255, varying = true), O.Default(""))
    /** Database column imported SqlType(BIT), Default(false) */
    val imported: Rep[Boolean] = column[Boolean]("imported", O.Default(false))
    /** Database column doNotImport SqlType(BIT), Default(false) */
    val donotimport: Rep[Boolean] = column[Boolean]("doNotImport", O.Default(false))

    def * = (id, name, email, imported, donotimport) <>(AuthorsRow.tupled, AuthorsRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), Rep.Some(name), Rep.Some(email), Rep.Some(imported), Rep.Some(donotimport)).shaped.<>({
        r => import r._; _1.map(_ => AuthorsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }
  }

  /** Collection-like TableQuery object for table Authors */
  lazy val Authors = new TableQuery(tag => new Authors(tag))



  /** Entity class storing rows of table Chapters
    *
    * @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
    * @param position Database column Position SqlType(INT), Default(None)
    * @param title Database column Title SqlType(VARCHAR), Length(255,true), Default()
    * @param authorid Database column AuthorID SqlType(INT), Default(0)
    * @param text Database column Text SqlType(LONGTEXT), Length(2147483647,true), Default(None)
    * @param date Database column Date SqlType(DATE), Default('0000-00-00')
    * @param storyid Database column StoryID SqlType(INT), Default(0)
    * @param notes Database column Notes SqlType(TEXT), Default(None)
    * @param url Database column Url SqlType(VARCHAR), Length(1024,true), Default(None) */
  case class ChaptersRow(id: Long, position: Option[Long] = None, title: String = "", authorid: Int = 0,
                         text: Option[String] = None, date: Option[java.sql.Date] = None, storyid: Long = 0,
                         notes: Option[String] = None, url: Option[String] = None)

  /** GetResult implicit for fetching ChaptersRow objects using plain SQL queries */
  implicit def GetResultChaptersRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[String], e3: GR[Option[String]],
                                    e4: GR[Option[java.sql.Date]]): GR[ChaptersRow] = {
    GR {
      prs => import prs._
        ChaptersRow
          .tupled((<<[Long], <<?[Long], <<[String], <<[Int], <<?[String], <<?[java.sql.Date], <<[Long], <<?[String], <<?[String]))
    }
  }

  /** Table description of table chapters. Objects of this class serve as prototypes for rows in queries. */
  class Chapters(_tableTag: Tag) extends Table[ChaptersRow](_tableTag, "chapters") {

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Position SqlType(INT), Default(None) */
    val position: Rep[Option[Long]] = column[Option[Long]]("Position", O.Default(None))
    /** Database column Title SqlType(VARCHAR), Length(255,true), Default() */
    val title: Rep[String] = column[String]("Title", O.Length(255, varying = true), O.Default(""))
    /** Database column AuthorID SqlType(INT), Default(0) */
    val authorid: Rep[Int] = column[Int]("AuthorID", O.Default(0))
    /** Database column Text SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val text: Rep[Option[String]] = column[Option[String]]("Text", O.Length(2147483647, varying = true), O.Default(None))
    /** Database column Date SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("Date", O.Default(None))
    /** Database column StoryID SqlType(INT), Default(0) */
    val storyid: Rep[Long] = column[Long]("StoryID", O.Default(0))
    /** Database column Notes SqlType(TEXT), Default(None) */
    val notes: Rep[Option[String]] = column[Option[String]]("Notes", O.Default(None))
    /** Database column Url SqlType(VARCHAR), Length(1024,true), Default(None) */
    val url: Rep[Option[String]] = column[Option[String]]("Url", O.Length(1024, varying = true), O.Default(None))

    /** Index over (storyid) (database name storyid) */
    val index1 = index("storyid", storyid)

    def * = (id, position, title, authorid, text, date, storyid, notes, url) <>(ChaptersRow.tupled, ChaptersRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), position, Rep.Some(title), Rep.Some(authorid), text, date, Rep
        .Some(storyid), notes, url).shaped.<>({ r => import r._;
        _1.map(_ => ChaptersRow.tupled((_1.get, _2, _3.get, _4
          .get, _5, _6, _7.get, _8, _9)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }
  }

  /** Collection-like TableQuery object for table Chapters */
  lazy val Chapters = new TableQuery(tag => new Chapters(tag))

  /** Entity class storing rows of table PlayEvolutions
    *
    * @param id Database column id SqlType(INT), PrimaryKey
    * @param hash Database column hash SqlType(VARCHAR), Length(255,true)
    * @param appliedAt Database column applied_at SqlType(TIMESTAMP)
    * @param applyScript Database column apply_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
    * @param revertScript Database column revert_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
    * @param state Database column state SqlType(VARCHAR), Length(255,true), Default(None)
    * @param lastProblem Database column last_problem SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None,
                               revertScript: Option[String] = None, state: Option[String] = None,
                               lastProblem: Option[String] = None)

  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp],
                                          e3: GR[Option[String]]): GR[PlayEvolutionsRow] = {
    GR {
      prs => import prs._
        PlayEvolutionsRow
          .tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
    }
  }

  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = {
      (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <>(PlayEvolutionsRow
        .tupled, PlayEvolutionsRow.unapply)
    }

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped
        .<>({ r => import r._; _1.map(_ => PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))
        }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }

    /** Database column id SqlType(INT), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(VARCHAR), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255, varying = true))
    /** Database column applied_at SqlType(TIMESTAMP) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Length(16777215, varying = true), O
      .Default(None))
    /** Database column revert_script SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O
      .Length(16777215, varying = true), O.Default(None))
    /** Database column state SqlType(VARCHAR), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255, varying = true), O.Default(None))
    /** Database column last_problem SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Length(16777215, varying = true), O
      .Default(None))
  }

  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

}
