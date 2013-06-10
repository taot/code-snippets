import java.io._

object TestRegex {

  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(new FileReader(args(0)))
    val ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(args(1))))
    
    val p = """(\d+)\tMember\((\d+)\)\*Name\((\d+)\)""".r
    var line = reader.readLine()
    while (line != null) {
      val p(a, b, c) = line
      ps.println("==> " + a + " " + b + " " + c)
//      println(a + " " + b + " " + c)
      line = reader.readLine()
    }
    reader.close()
    ps.close()
  }

}
