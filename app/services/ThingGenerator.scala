package services

import models.{Story, Author}
import org.joda.time.DateTime

import scala.util.Random

trait ThingGenerator {

  val ratings = List("Not Rated", "General Audiences", "Teen And Up Audiences", "Mature", "Explicit")

  val urls = List("", "foo", "http://astele.co.uk/od-test/api-test-1.htm",
                  "http://vmguest/WolfAndHound/Chapter/Details/861", "http://astele.co.uk/henneth/Chapter/Details/6318")

  lazy val generatedAuthors: List[Author] = List.tabulate(5) { x =>
    Author(x, s"author$x", s"author$x@example.com").copy( stories = Some(stories(x)))
  }


  def stories(id: Int) = List.tabulate((new Random).nextInt(10)) { x =>
    Story(Some(x),
          s"Story $x",
          Some("a story about a thing"),
          authorID = id,
          rating = Random.shuffle(ratings).head,
          date = DateTime.now(),
          tags = Some("thing"),
          url = Random.shuffle(urls).headOption
    )
  }
}
