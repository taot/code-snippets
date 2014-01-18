package child1

import child2.Child2

class Child1 {

  def hi(name: String): Unit = {
    println("Sub project child1: hi " + name)
    val child2 = new Child2
    child2.hi("Terry")
  }
}
