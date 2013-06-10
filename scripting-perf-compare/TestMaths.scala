object TestMaths {
  
  def main(args: Array[String]): Unit = {
    var sum = 0.0
    for (i <- 0 until 100000000) {
      sum += math.sqrt(i)
    }
    println("sum = " + sum)
  }
}
