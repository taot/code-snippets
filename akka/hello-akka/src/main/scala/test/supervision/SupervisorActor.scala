package test.supervision

import akka.actor._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.SupervisorStrategy.{Resume, Restart, Escalate, Stop}

object SupervisorActor {
  case object Start
}

class SupervisorActor extends Actor with ActorLogging {

  import SupervisorActor.Start

  val child = context.actorOf(Props[FailingActor], "failing-actor-1")

  context.watch(child)

  def receive = {
    case Start =>
      log.info("Received start. Sending message to child")
      context.system.scheduler.scheduleOnce(1.seconds, child, FailingActor.Normal("normal"))
      context.system.scheduler.scheduleOnce(2.seconds, child, FailingActor.ToFail("to fail"))
      context.system.scheduler.scheduleOnce(5.seconds, child, FailingActor.ToFail("to fail again"))
    case Terminated(actor) =>
      log.info(actor.path + " terminated")
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _ => Restart
  }
}
