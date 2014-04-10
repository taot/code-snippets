package com.taot.restful

import akka.actor.{Props, ActorSystem}
import spray.can.Http
import akka.io.IO


object Boot {

  private implicit val system = ActorSystem("pms-web-actor-system")

  private val service = system.actorOf(Props[ServiceActor], "pms-web-service")

  private val users = List(User("Terry", "Tao", 30), User("Zhuoran", "Wang", 25))

  def main(args: Array[String]): Unit = {
    for (u <- users) UserManager.add(u)
    IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)
  }
}
