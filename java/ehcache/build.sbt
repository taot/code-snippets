name := "ehcache"

version := "0.1"

scalaVersion := "2.10.1"


resolvers += "JBoss maven repository" at "http://repository.jboss.org/nexus/content/groups/public"

libraryDependencies  ++=  Seq(
  "junit" % "junit" % "4.11",
  //"org.specs2" % "specs2_2.9.1" % "1.12.4",
  //"org.scalamock" % "scalamock-specs2-support_2.9.1" % "2.4",
  "org.infinispan" % "infinispan-core" % "5.3.0.Final",
  "org.infinispan" % "infinispan-query" % "5.3.0.Final",
  //"org.infinispan" % "infinispan-core" % "6.0.0.Beta2",
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.scala-tools.testing" % "specs_2.10" % "1.6.9",
  "net.sf.ehcache" % "ehcache" % "2.7.4"
)
