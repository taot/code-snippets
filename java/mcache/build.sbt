name := "mcache"

version := "0.1"

scalaVersion := "2.10.1"

libraryDependencies  ++=  Seq(
  "com.novocode" % "junit-interface" % "0.10",
  "junit" % "junit" % "4.11",
  "org.apache.geronimo.specs" % "geronimo-jpa_2.0_spec" % "1.1",
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.scala-tools.testing" % "specs_2.10" % "1.6.9",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)
