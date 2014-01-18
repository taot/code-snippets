package use

import generated.A

object MyUse {

  def main(args: Array[String]): Unit = {
    println("Sub project child1: hi ")
    val a = new A
    a.hi()
  }
}
