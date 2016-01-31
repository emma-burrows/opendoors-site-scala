import com.typesafe.config._
import com.typesafe.sbt.SbtNativePackager.autoImport._

val conf = ConfigFactory.parseFile(new File("conf/server.conf")).resolve()

val sitename = conf.getString("application.name")

import sbt.Keys._

lazy val opendoorsSite = (project in file(".")).enablePlugins(PlayScala)
  .settings(
    name := sitename,
    packageName in Universal := sitename,
    version := "1.0",
    scalaVersion := "2.11.7",
    slick <<= slickCodeGenTask)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  specs2 % Test,
  // WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "2.1.3",
  "org.webjars" % "bootstrap" % "3.3.6",

  "mysql" % "mysql-connector-java" % "5.1.38",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",

  "org.otw" %% "archive-client" % "0.1-SNAPSHOT"
)


fork in run := true

// do not include docs in distribution file
sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
  val url = "jdbc:mysql://localhost/foofoo"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "models.db"
  toError(r.run("slick.codegen.SourceCodeGenerator",
                cp.files,
                Array(slickDriver, jdbcDriver, url, outputDir, pkg,
                      conf.getString("slick.dbs.default.db.user"),
                      conf.getString("slick.dbs.default.db.password")),
                s.log))
  val fname = outputDir + "/demo/Tables.scala"
  Seq(file(fname))
}
