import com.typesafe.config._

val conf = ConfigFactory.parseFile(new File("conf/server.conf")).resolve()

val sitename = conf.getString("application.name")

import sbt.Keys._

name := sitename

packageName in Universal := sitename

version := "1.0"

lazy val opendoorsSite = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  specs2 % Test,
  // WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "2.1.3",
  "org.webjars" % "bootstrap" % "3.3.6",

  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",

  "org.otw" %% "archive-client" % "0.1-SNAPSHOT"
)


fork in run := true

// do not include docs in distribution file
sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

