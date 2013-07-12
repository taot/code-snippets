name := "squeryl-example"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies  ++=  Seq(
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  "mysql" % "mysql-connector-java" % "5.1.25"
)
