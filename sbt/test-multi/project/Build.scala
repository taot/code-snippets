import sbt._
import Keys._

object TestMultiBuild extends Build {

//  val version = "1.0"

  lazy val testMulti = Project(id = "testMulti", base = file("."))
    .aggregate(child1, child2).dependsOn(child1)

  lazy val child1 = Project(id = "child1", base = file("child1")) dependsOn(child2)

  lazy val child2 = Project(id = "child2", base = file("child2"))
}
