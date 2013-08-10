name := "cake-pattern-example"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies  ++=  Seq(
  "junit" % "junit" % "4.11",
  //"org.specs2" % "specs2_2.9.1" % "1.12.4",
  //"org.scalamock" % "scalamock-specs2-support_2.9.1" % "2.4",
  "org.scala-tools.testing" % "specs_2.10" % "1.6.9",
  "org.mockito" % "mockito-core" % "1.9.5"
)
