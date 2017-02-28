package controllers

import java.util.Date

import com.typesafe.config.Config
import models._
import org.joda.time.DateTime
import org.scalatest.Matchers
import org.scalatest.mock.MockitoSugar
import org.mockito.ArgumentMatchers.{any => anyArg}
import org.mockito.BDDMockito.given
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import otw.api.response.{WorkFoundResponse, FindWorkResponse}
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.{Environment, Configuration}
import services.MySqlDatabase

import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.duration._
import scala.language.postfixOps


import scala.util.Random

class AuthorsControllerTest extends PlaySpec with Matchers with MockitoSugar with Results with OneAppPerSuite {

  val mockDataService = mock[MySqlDatabase]
  val mockConfig = mock[Config]
  val mockArchive = mock[utils.Archive]
  val env: Environment = Environment.simple()
  val ec: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  val authorController = new AuthorsController(env, Configuration(mockConfig), mockDataService, mockArchive)


  "AuthorsController" should {

    "authorFuture" in new Fixture {
      val thing = Await.result(authorController.authorFuture(1), 10 seconds)
      thing must not be Nil
    }

    "appName" in {
      pending
    }

    "doNotImportAll" in {
      pending
    }

    "findAll" in new Fixture {
      val thing = authorController.findAll(1)(FakeRequest())
      println(contentAsString(thing))
      contentAsString(thing) must include ("Author 1")
      contentAsString(thing) must include ("Stories")
    }

    "list" in new Fixture {
      val thing = authorController.list()(FakeRequest())
      println(contentAsString(thing))
      contentAsString(thing) must include ("Author 1")
      contentAsString(thing) must include ("Stories")
    }

    "Found" in {
      pending
    }

    "archive" in {
      pending
    }

    "importAll" in {
      pending
    }

  }


  trait Fixture {
    val author1 = Author(1, "Author 1", "author@ao3.org", imported = false, doNotImport = false)
    val authorWW1 = AuthorWithWorks(author1, Some(ThingGenerator.stories(1)))

    given(mockDataService.archiveconfig) willReturn Future.successful(ArchiveConfig())
    given(mockDataService.authorsWithItems()) willReturn Future.successful(Seq(authorWW1))

    given(mockArchive.client.findUrls(anyArg())(ec))
      .willReturn(Future.successful(Right(FindWorkResponse(200, List(WorkFoundResponse("ok", "1", "something", "ao3.url", DateTime.now))))))
  }

  object ThingGenerator {

    lazy val generatedAuthors: List[Author] = List.tabulate(5) { x =>
      Author(x, s"author$x", s"author$x@example.com") // .copy( stories = Some(stories(x)))
    }
    val ratings = List("Not Rated", "General Audiences", "Teen And Up Audiences", "Mature", "Explicit")
    val urls = List("", "foo", "http://astele.co.uk/od-test/api-test-1.htm",
                    "http://vmguest/WolfAndHound/Chapter/Details/861", "http://astele.co" +
                      ".uk/henneth/Chapter/Details/6318")

    def stories(authorId: Int) = List.tabulate((new Random).nextInt(10)) { x =>
      StoryWithChapters(
        Story(
          x,
          s"Story $x",
          Some("a story about a thing"),
          authorID = authorId,
          rating = Random.shuffle(ratings).head,
          date = Some(new java.sql.Date(DateTime.now().getMillis)),
          updated = Some(new java.sql.Date(DateTime.now().getMillis)),
          tags = Some("thing"),
          url = Random.shuffle(urls).headOption
        ),
        Option(
          List(
            Chapter(
              ID = x + 10,
              position = Option(1),
              title = "Chapter Title",
              authorid = authorId,
              text = Some("This is the text of the chapter"),
              storyid = x,
              date = Some(new Date(2015, 12, 2)),
              notes = None,
              url = None
            )
          )
        )
      )
    }.toSeq

  }

}
