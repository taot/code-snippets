package test.supervision

import akka.actor.{Props, ActorSystem}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object TestSupervision extends App {

  val system = ActorSystem("TestSupervision")
  val actor = system.actorOf(Props[SupervisorActor], "supervisor-actor")
  system.scheduler.scheduleOnce(1.seconds, actor, SupervisorActor.Start)
  system.scheduler.scheduleOnce(100.seconds) {
    system.shutdown()
  }
}
