package test.supervision

import akka.actor.{ActorLogging, Actor}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FailingActor {

  case class ToFail(msg: String)

  case class Normal(msg: String)

  case object Tick

}

class FailingActor extends Actor with ActorLogging {

  import FailingActor._

  var counter = 0

  override def preStart(): Unit = {
    log.info("preStart")
    context.system.scheduler.schedule(10.millis, 10.millis, self, Tick)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    super.preRestart(reason, message)
    log.info("preRestart: counter = " + counter)
  }

  override def postRestart(reason: Throwable): Unit = {
    super.postRestart(reason)
    log.info("postRestart: counter = " + counter)
  }

  def receive = {
    case Normal(msg) =>
      log.info("Normal received: " + msg)
    case ToFail(msg) =>
      log.info("ToFail received: " + msg)
      throw new RuntimeException(msg)
    case Tick => counter += 1
  }
}
