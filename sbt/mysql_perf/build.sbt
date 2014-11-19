name := "mysql_perf"

scalaVersion in ThisBuild := "2.10.2"

version in ThisBuild := "1.0-SNAPSHOT"

sbtVersion in ThisBuild := "13.0"

libraryDependencies ++= Seq(
  "com.zaxxer" % "HikariCP-java6" % "2.2.5",
  "mysql" % "mysql-connector-java" % "5.1.25"
)