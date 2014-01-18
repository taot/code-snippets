package main

import child1.Child1

object TestMain extends App {

  println("hello")
  val child1 = new Child1
  child1.hi("Terry")
}
