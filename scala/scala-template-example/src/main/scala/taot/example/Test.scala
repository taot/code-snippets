package taot.example

import java.util.File
import play.templates.ScalaTemplateCompiler

object Test  {

  def main(args: Array[String]): Unit = {
    println("Start...")
    val baseDir = new File("/home/taot/tmp/scala-template/")
    val sourceFile = new File(baseDir, "src/TestTemplate.html")
    val sourceDir = new File(baseDir, "src")
    val generatedDir = new File(baseDir, "target")
    ScalaTemplateCompiler.compile(sourceFile, sourceDir, generatedDir, "Class", "")
  }
}
