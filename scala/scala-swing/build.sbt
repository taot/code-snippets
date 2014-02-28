name := "pms-mini"

scalaVersion := "2.10.2"

version := "0.1-SNAPSHOT"

sbtVersion := "13.0"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "org.scala-lang" % "scala-actors" % "2.10.2",
  "org.scala-lang" % "scala-swing" % "2.10.2",
  "org.slf4j" % "slf4j-api" % "1.7.2"
)

