package models.db

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction

  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Authors.schema ++ Bookmarks.schema ++ Chapters.schema ++ PlayEvolutions
    .schema ++ Stories.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = {
    schema
  }

  /** Entity class storing rows of table Authors
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

  /** Entity class storing rows of table Bookmarks
    * @param id Database column id SqlType(INT), AutoInc, PrimaryKey
    * @param title Database column title SqlType(VARCHAR), Length(255,true), Default()
    * @param summary Database column summary SqlType(TEXT), Default(None)
    * @param notes Database column notes SqlType(TEXT), Default(None)
    * @param authorid Database column authorid SqlType(INT), Default(0)
    * @param rating Database column rating SqlType(VARCHAR), Length(255,true), Default()
    * @param date Database column date SqlType(DATE)
    * @param tags Database column tags SqlType(VARCHAR), Length(255,true), Default()
    * @param warnings Database column warnings SqlType(VARCHAR), Length(255,true), Default(Some())
    * @param fandoms Database column fandoms SqlType(VARCHAR), Length(255,true), Default(None)
    * @param characters Database column characters SqlType(VARCHAR), Length(255,true), Default(None)
    * @param relationships Database column relationships SqlType(VARCHAR), Length(255,true), Default(None)
    * @param url Database column url SqlType(VARCHAR), Length(255,true), Default(None)
    * @param imported Database column imported SqlType(BIT), Default(false)
    * @param donotimport Database column doNotImport SqlType(BIT), Default(false)
    * @param ao3url Database column Ao3Url SqlType(VARCHAR), Length(255,true), Default(None) */
  case class BookmarksRow(id: Long, title: String = "", summary: Option[String] = None, notes: Option[String] = None,
                          authorid: Long = 0, rating: String = "", date: java.sql.Date, tags: Option[String] = None,
                          warnings: Option[String] = Some(""), fandoms: Option[String] = None,
                          characters: Option[String] = None, relationships: Option[String] = None,
                          url: Option[String] = None, imported: Boolean = false, donotimport: Boolean = false,
                          ao3url: Option[String] = None)

  /** GetResult implicit for fetching BookmarksRow objects using plain SQL queries */
  implicit def GetResultBookmarksRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]],
                                     e3: GR[java.sql.Date], e4: GR[Boolean]): GR[BookmarksRow] = {
    GR {
      prs => import prs._
        BookmarksRow
          .tupled((<<[Long], <<[String], <<?[String], <<?[String], <<[Int], <<[String], <<[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean], <<[Boolean], <<?[String]))
    }
  }

  /** Table description of table bookmarks. Objects of this class serve as prototypes for rows in queries. */
  class Bookmarks(_tableTag: Tag) extends Table[BookmarksRow](_tableTag, "bookmarks") {

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(255,true), Default() */
    val title: Rep[String] = column[String]("title", O.Length(255, varying = true), O.Default(""))
    /** Database column summary SqlType(TEXT), Default(None) */
    val summary: Rep[Option[String]] = column[Option[String]]("summary", O.Default(None))
    /** Database column notes SqlType(TEXT), Default(None) */
    val notes: Rep[Option[String]] = column[Option[String]]("notes", O.Default(None))
    /** Database column authorid SqlType(INT), Default(0) */
    val authorid: Rep[Long] = column[Long]("authorid", O.Default(0))
    /** Database column rating SqlType(VARCHAR), Length(255,true), Default() */
    val rating: Rep[String] = column[String]("rating", O.Length(255, varying = true), O.Default(""))
    /** Database column date SqlType(DATE) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
    /** Database column tags SqlType(VARCHAR), Length(255,true), Default() */
    val tags: Rep[Option[String]] = column[Option[String]]("tags", O.Length(1024, varying = true))
    /** Database column warnings SqlType(VARCHAR), Length(255,true), Default(Some()) */
    val warnings: Rep[Option[String]] = column[Option[String]]("warnings", O.Length(255, varying = true), O
      .Default(Some("")))
    /** Database column fandoms SqlType(VARCHAR), Length(255,true), Default(None) */
    val fandoms: Rep[Option[String]] = column[Option[String]]("fandoms", O.Length(255, varying = true), O.Default(None))
    /** Database column characters SqlType(VARCHAR), Length(255,true), Default(None) */
    val characters: Rep[Option[String]] = column[Option[String]]("characters", O.Length(255, varying = true), O
      .Default(None))
    /** Database column relationships SqlType(VARCHAR), Length(255,true), Default(None) */
    val relationships: Rep[Option[String]] = column[Option[String]]("relationships", O.Length(255, varying = true), O
      .Default(None))
    /** Database column url SqlType(VARCHAR), Length(255,true), Default(None) */
    val url: Rep[Option[String]] = column[Option[String]]("url", O.Length(255, varying = true), O.Default(None))
    /** Database column imported SqlType(BIT), Default(false) */
    val imported: Rep[Boolean] = column[Boolean]("imported", O.Default(false))
    /** Database column doNotImport SqlType(BIT), Default(false) */
    val donotimport: Rep[Boolean] = column[Boolean]("doNotImport", O.Default(false))
    /** Database column Ao3Url SqlType(VARCHAR), Length(255,true), Default(None) */
    val ao3url: Rep[Option[String]] = column[Option[String]]("Ao3Url", O.Length(255, varying = true), O.Default(None))


    /** Index over (authorid) (database name authorid) */
    val index1 = index("authorid", authorid)

    def * = {
      (id, title, summary, notes, authorid, rating, date, tags, warnings, fandoms, characters, relationships, url, imported, donotimport, ao3url) <>(BookmarksRow
        .tupled, BookmarksRow.unapply)
    }

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), Rep.Some(title), summary, notes, Rep.Some(authorid), Rep.Some(rating), Rep.Some(date), Rep
        .Some(tags), warnings, fandoms, characters, relationships, url, Rep.Some(imported), Rep
        .Some(donotimport), ao3url)
        .shaped.<>({ r => import r._;
        _1.map(_ => BookmarksRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6.get, _7.get, _8
          .get, _9, _10, _11, _12, _13, _14.get, _15.get, _16)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }

  }

  /** Collection-like TableQuery object for table Bookmarks */
  lazy val Bookmarks = new TableQuery(tag => new Bookmarks(tag))

  /** Entity class storing rows of table Chapters
    * @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
    * @param position Database column Position SqlType(BIGINT), Default(None)
    * @param title Database column Title SqlType(VARCHAR), Length(255,true), Default()
    * @param authorid Database column AuthorID SqlType(INT), Default(0)
    * @param text Database column Text SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
    * @param date Database column Date SqlType(DATE)
    * @param storyid Database column StoryID SqlType(INT), Default(0)
    * @param notes Database column Notes SqlType(TEXT), Default(None)
    * @param url Database column Url SqlType(VARCHAR), Length(1024,true), Default(None) */
  case class ChaptersRow(id: Long, position: Option[Long] = None, title: String = "", authorid: Int = 0,
                         text: Option[String] = None, date: java.sql.Date, storyid: Long = 0,
                         notes: Option[String] = None, url: Option[String] = None)

  /** GetResult implicit for fetching ChaptersRow objects using plain SQL queries */
  implicit def GetResultChaptersRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[String], e3: GR[Option[String]],
                                    e4: GR[java.sql.Date]): GR[ChaptersRow] = {
    GR {
      prs => import prs._
        ChaptersRow
          .tupled((<<[Long], <<?[Long], <<[String], <<[Int], <<?[String], <<[java.sql.Date], <<[Long], <<?[String], <<?[String]))
    }
  }

  /** Table description of table chapters. Objects of this class serve as prototypes for rows in queries. */
  class Chapters(_tableTag: Tag) extends Table[ChaptersRow](_tableTag, "chapters") {

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Position SqlType(BIGINT), Default(None) */
    val position: Rep[Option[Long]] = column[Option[Long]]("Position", O.Default(None))
    /** Database column Title SqlType(VARCHAR), Length(255,true), Default() */
    val title: Rep[String] = column[String]("Title", O.Length(255, varying = true), O.Default(""))
    /** Database column AuthorID SqlType(INT), Default(0) */
    val authorid: Rep[Int] = column[Int]("AuthorID", O.Default(0))
    /** Database column Text SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val text: Rep[Option[String]] = column[Option[String]]("Text", O.Length(16777215, varying = true), O.Default(None))
    /** Database column Date SqlType(DATE) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("Date")
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
      (Rep.Some(id), position, Rep.Some(title), Rep.Some(authorid), text, Rep.Some(date), Rep
        .Some(storyid), notes, url).shaped.<>({ r => import r._;
        _1.map(_ => ChaptersRow.tupled((_1.get, _2, _3.get, _4
          .get, _5, _6.get, _7.get, _8, _9)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }
  }

  /** Collection-like TableQuery object for table Chapters */
  lazy val Chapters = new TableQuery(tag => new Chapters(tag))

  /** Entity class storing rows of table PlayEvolutions
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

  /** Entity class storing rows of table Stories
    * @param id Database column id SqlType(INT), AutoInc, PrimaryKey
    * @param title Database column title SqlType(VARCHAR), Length(255,true), Default()
    * @param summary Database column summary SqlType(TEXT), Default(None)
    * @param notes Database column notes SqlType(TEXT), Default(None)
    * @param authorid Database column authorid SqlType(INT), Default(0)
    * @param rating Database column rating SqlType(VARCHAR), Length(255,true), Default()
    * @param date Database column date SqlType(DATE)
    * @param tags Database column tags SqlType(VARCHAR), Length(255,true), Default()
    * @param warnings Database column warnings SqlType(VARCHAR), Length(255,true), Default(Some())
    * @param fandoms Database column fandoms SqlType(VARCHAR), Length(255,true), Default(None)
    * @param characters Database column characters SqlType(VARCHAR), Length(255,true), Default(None)
    * @param relationships Database column relationships SqlType(VARCHAR), Length(255,true), Default(None)
    * @param url Database column url SqlType(VARCHAR), Length(255,true), Default(None)
    * @param imported Database column imported SqlType(BIT), Default(false)
    * @param donotimport Database column doNotImport SqlType(BIT), Default(false)
    * @param ao3url Database column Ao3Url SqlType(VARCHAR), Length(255,true), Default(None) */
  case class StoriesRow(id: Long, title: String = "", summary: Option[String] = None, notes: Option[String] = None,
                        authorid: Long = 0, rating: String = "", date: java.sql.Date, tags: Option[String] = None,
                        warnings: Option[String] = Some(""), fandoms: Option[String] = None,
                        characters: Option[String] = None, relationships: Option[String] = None,
                        url: Option[String] = None, imported: Boolean = false, donotimport: Boolean = false,
                        ao3url: Option[String] = None)

  /** GetResult implicit for fetching StoriesRow objects using plain SQL queries */
  implicit def GetResultStoriesRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Date],
                                   e4: GR[Boolean]): GR[StoriesRow] = {
    GR {
      prs => import prs._
        StoriesRow
          .tupled((<<[Long], <<[String], <<?[String], <<?[String], <<[Long], <<[String], <<[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[Boolean], <<[Boolean], <<?[String]))
    }
  }

  /** Table description of table stories. Objects of this class serve as prototypes for rows in queries. */
  class Stories(_tableTag: Tag) extends Table[StoriesRow](_tableTag, "stories") {

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(255,true), Default() */
    val title: Rep[String] = column[String]("title", O.Length(255, varying = true), O.Default(""))
    /** Database column summary SqlType(TEXT), Default(None) */
    val summary: Rep[Option[String]] = column[Option[String]]("summary", O.Default(None))
    /** Database column notes SqlType(TEXT), Default(None) */
    val notes: Rep[Option[String]] = column[Option[String]]("notes", O.Default(None))
    /** Database column authorid SqlType(INT), Default(0) */
    val authorid: Rep[Long] = column[Long]("authorid", O.Default(0))
    /** Database column rating SqlType(VARCHAR), Length(255,true), Default() */
    val rating: Rep[String] = column[String]("rating", O.Length(255, varying = true), O.Default(""))
    /** Database column date SqlType(DATE) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
    /** Database column tags SqlType(VARCHAR), Length(255,true), Default() */
    val tags: Rep[Option[String]] = column[Option[String]]("tags", O.Length(1024, varying = true))
    /** Database column warnings SqlType(VARCHAR), Length(255,true), Default(Some()) */
    val warnings: Rep[Option[String]] = column[Option[String]]("warnings", O.Length(255, varying = true), O
      .Default(Some("")))
    /** Database column fandoms SqlType(VARCHAR), Length(255,true), Default(None) */
    val fandoms: Rep[Option[String]] = column[Option[String]]("fandoms", O.Length(255, varying = true), O.Default(None))
    /** Database column characters SqlType(VARCHAR), Length(255,true), Default(None) */
    val characters: Rep[Option[String]] = column[Option[String]]("characters", O.Length(255, varying = true), O
      .Default(None))
    /** Database column relationships SqlType(VARCHAR), Length(255,true), Default(None) */
    val relationships: Rep[Option[String]] = column[Option[String]]("relationships", O.Length(255, varying = true), O
      .Default(None))
    /** Database column url SqlType(VARCHAR), Length(255,true), Default(None) */
    val url: Rep[Option[String]] = column[Option[String]]("url", O.Length(255, varying = true), O.Default(None))
    /** Database column imported SqlType(BIT), Default(false) */
    val imported: Rep[Boolean] = column[Boolean]("imported", O.Default(false))
    /** Database column doNotImport SqlType(BIT), Default(false) */
    val donotimport: Rep[Boolean] = column[Boolean]("doNotImport", O.Default(false))
    /** Database column Ao3Url SqlType(VARCHAR), Length(255,true), Default(None) */
    val ao3url: Rep[Option[String]] = column[Option[String]]("Ao3Url", O.Length(255, varying = true), O.Default(None))

    /** Index over (authorid) (database name authorid) */
    val index1 = index("authorid", authorid)

    def * = {
      (id, title, summary, notes, authorid, rating, date, tags, warnings, fandoms, characters, relationships, url, imported, donotimport, ao3url) <>(StoriesRow
        .tupled, StoriesRow.unapply)
    }

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), Rep.Some(title), summary, notes, Rep.Some(authorid), Rep.Some(rating), Rep.Some(date), Rep
        .Some(tags), warnings, fandoms, characters, relationships, url, Rep.Some(imported), Rep
        .Some(donotimport), ao3url)
        .shaped.<>({ r => import r._;
        _1.map(_ => StoriesRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6.get, _7.get, _8
          .get, _9, _10, _11, _12, _13, _14.get, _15.get, _16)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }
  }

  /** Collection-like TableQuery object for table Stories */
  lazy val Stories = new TableQuery(tag => new Stories(tag))
}
