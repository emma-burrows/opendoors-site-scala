package models.db

import slick.jdbc._
import slick.lifted.TableQuery

trait StoriesTable {

  self: Tables =>

  /** Collection-like TableQuery object for table Stories */
  lazy val Stories = new TableQuery(tag => new Stories(tag))

  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.jdbc.{GetResult => GR}

  /** GetResult implicit for fetching StoriesRow objects using plain SQL queries */
  implicit def GetResultStoriesRow(implicit e0: GR[Long],
    e1: GR[String],
    e2: GR[Option[String]],
    e3: GR[Option[java.sql.Date]],
    e4: GR[Boolean]): GR[StoriesRow] = {
    GR {
      prs => import prs._
        StoriesRow
          .tupled((<<[Long], <<[String], <<?[String], <<?[String], <<[Long], <<[String], <<?[java.sql.Date],
                    <<?[java.sql.Date], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String],
                    <<?[String], <<[Boolean], <<[Boolean], <<?[String]))
    }
  }

  /** Entity class storing rows of table Stories
    *
    */
  case class StoriesRow(id: Long,
    title: String = "",
    summary: Option[String] = None,
    notes: Option[String] = None,
    authorid: Long = 0,
    rating: String = "",
    date: Option[java.sql.Date] = None,
    updated: Option[java.sql.Date] = None,
    categories: Option[String] = None,
    tags: Option[String] = None,
    warnings: Option[String] = None,
    fandoms: Option[String] = None,
    characters: Option[String] = None,
    relationships: Option[String] = None,
    url: Option[String] = None,
    imported: Boolean = false,
    donotimport: Boolean = false,
    ao3url: Option[String] = None)

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
    /** Database column rating SqlType(VARCHAR), Length(25,true), Default() */
    val rating: Rep[String] = column[String]("rating", O.Length(25, varying = true), O.Default(""))
    /** Database column date SqlType(DATE), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
    /** Database column updated SqlType(DATE), Default(None) */
    val updated: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("updated", O.Default(None))
    /** Database column categories SqlType(VARCHAR), Length(45,true), Default(None) */
    val categories: Rep[Option[String]] = column[Option[String]]("categories", O.Length(45, varying = true), O
      .Default(None))
    /** Database column tags SqlType(VARCHAR), Length(1024,true), Default(None) */
    val tags: Rep[Option[String]] = column[Option[String]]("tags", O.Length(1024, varying = true), O.Default(None))
    /** Database column warnings SqlType(VARCHAR), Length(255,true), Default(None) */
    val warnings: Rep[Option[String]] = column[Option[String]]("warnings", O.Length(255, varying = true), O
      .Default(None))
    /** Database column fandoms SqlType(VARCHAR), Length(255,true), Default(None) */
    val fandoms: Rep[Option[String]] = column[Option[String]]("fandoms", O.Length(255, varying = true), O.Default(None))
    /** Database column characters SqlType(VARCHAR), Length(1024,true), Default(None) */
    val characters: Rep[Option[String]] = column[Option[String]]("characters", O.Length(1024, varying = true), O
      .Default(None))
    /** Database column relationships SqlType(VARCHAR), Length(1024,true), Default(None) */
    val relationships: Rep[Option[String]] = column[Option[String]]("relationships", O.Length(1024, varying = true), O
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
      (id, title, summary, notes, authorid, rating, date, updated, categories, tags, warnings, fandoms, characters,
        relationships, url, imported, donotimport, ao3url)
        .<>(StoriesRow.tupled, StoriesRow.unapply)
    }

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = {
      (Rep.Some(id), Rep.Some(title), summary, notes, Rep.Some(authorid), Rep.Some(rating), date, Rep
        .Some(updated), categories,
        tags, warnings, fandoms, characters, relationships, url, Rep.Some(imported), Rep.Some(donotimport), ao3url)
        .shaped.<>({ r => import r._;
        _1.map(_ => StoriesRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6.get, _7, _8
          .get, _9, _10, _11, _12, _13, _14, _15, _16.get, _17.get, _18)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    }
  }
}
