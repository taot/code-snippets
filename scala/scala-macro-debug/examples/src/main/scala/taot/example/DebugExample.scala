package taot.example

object DebugExample {

  def main(args: Array[String]): Unit = {
    import taot.debug.DebugMacros._
//    hello()
    val a = 1
    val b = 2
    val c = BigDecimal(1)
//    printparam(a)
//    debug(c.bigDecimal)
    debug2("I'm debugging", a, b, c)
  }
}