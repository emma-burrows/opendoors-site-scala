import com.typesafe.config._
import com.typesafe.sbt.SbtNativePackager.autoImport._
import sbt.Keys._

val conf = ConfigFactory.parseFile(new File("conf/server.conf")).resolve()

val sitename = conf.getString("application.name")


lazy val opendoorsSite = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := sitename,
    packageName in Universal := sitename,
    mappings in Universal += {
      file("logs") -> "logs"
    },
    version := "1.0",
    scalaVersion := "2.12.6",
    maintainer in Linux := "OTW Code <otw-coders@transformativeworks.org>",
    packageSummary in Linux := s"Open Doors temporary site '$sitename'",
//    slick <<= slickCodeGenTask
  )

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  guice,
  specs2 % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.github.tomakehurst" % "wiremock" % "1.57" % Test,
  "org.mockito" % "mockito-core" % "2.18.3" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,



// WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.6.3" exclude ("org.webjars", "jquery"),
  "org.webjars" % "jquery"        % "3.3.1-1",
  "org.webjars" % "bootstrap"     % "4.1.0",

  "mysql"               % "mysql-connector-java"    % "5.1.38",
  "com.typesafe.play"   %% "play-slick"             % "3.0.3",
  "com.typesafe.play"   %% "play-slick-evolutions"  % "3.0.3",
  "com.typesafe.slick"  %% "slick-codegen"          % "3.2.3",
  "org.json4s"          %% "json4s-native"          % "3.5.4",
  "org.json4s"          %% "json4s-ext"             % "3.5.4",

  "net.databinder.dispatch" %% "dispatch-core" % "0.13.4",
  "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.13.4"

)

fork in run := true

// do not include docs in distribution file
sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
//lazy val slickCodeGenTask =
//  (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
//  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
//  val url = "jdbc:mysql://localhost/foofoo"
//  val jdbcDriver = "com.mysql.jdbc.Driver"
//  val slickDriver = "slick.driver.MySQLDriver"
//  val pkg = "models.db"
//  toError(r.run("slick.codegen.SourceCodeGenerator",
//                cp.files,
//                Array(slickDriver, jdbcDriver, url, outputDir, pkg,
//                      conf.getString("slick.dbs.default.db.user"),
//                      conf.getString("slick.dbs.default.db.password")),
//                s.log))
//  val fname = outputDir + "/demo/Tables.scala"
//  Seq(file(fname))
//}
