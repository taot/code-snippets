package gen

import java.io._
import scala.io.Source

object Generator {

  def main(args: Array[String]): Unit = {
    if (args.size < 3) {
      println("ERROR: invalid arguments")
      System.exit(-1)
    }
    val configDir = new File(args(0))
    val outputDir = new File(args(1), "/gen")
    val listFile = new File(args(2))
    outputDir.mkdirs()

    val lines = Source.fromFile(new File(configDir, "codegen.conf")).getLines.toList
    println(lines)

    val ret = try {
      val files = for {
        name <- lines
        file = writeSourceFile(name, outputDir)
      } yield file

      val pw = new PrintWriter(new FileOutputStream(listFile))
      files.map { f =>
        pw.println(f.getAbsolutePath)
      }
      pw.close()

      0  // exit value
    } catch {
      case th: Throwable => -1
    }
    System.exit(ret)
  }

  private def writeSourceFile(name: String, dir: File): File = {
    val className = name
    val file = new File(dir, className + ".scala")
    val pw = new PrintWriter(new FileOutputStream(file))
    try {
      pw.println("package generated")
      pw.println(s"class $className {")
      pw.println("""  def hi(): Unit = println("hello")""")
      pw.println("}")
      file
    } finally {
      if (pw != null) pw.close()
    }
  }
}
