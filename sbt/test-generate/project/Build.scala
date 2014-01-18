import sbt._
import Keys._

object TestMultiBuild extends Build {

  lazy val testGenerator = Project(id = "test-generator", base = file("."))
    .aggregate(gen, use).dependsOn(gen, use)


  lazy val gen = Project(id = "gen", base = file("gen"))

  val myCodeGenerator = TaskKey[Seq[File]]("mycode-generator", "Generate my code")

  lazy val use = Project(id = "use", base = file("use"),

    settings = Defaults.defaultSettings ++ Seq(

      sourceGenerators in Compile <+= (myCodeGenerator in Compile),

      myCodeGenerator in Compile <<= (sourceManaged in Compile, resourceDirectory in Compile in gen, dependencyClasspath in Runtime in gen) map { (src, resource, cp) =>
        runMyCodeGenerator(src, resource, cp.files)
      }
    )
  )

  def runMyCodeGenerator(src: File, resource: File, cp: Seq[File]): Seq[File] = {
    println("runMyCodeGenerator - src: " + src.getAbsolutePath)
    println("resource: " + resource.getAbsolutePath)
    val mainClass = "gen.Generator"
    val tmp = java.io.File.createTempFile("source_tmp", ".scala")

    val i = new Fork.ForkScala(mainClass).fork(None, Nil, cp, Seq(resource.getAbsolutePath, src.getAbsolutePath, tmp.getAbsolutePath), None, false, StdoutOutput).exitValue()
    if (i != 0) {
      error("Trouble with code generator")
    }

    scala.io.Source.fromFile(tmp).getLines.map(f => file(f)).toList
  }

}
