package avionics

import akka.actor.{Actor, ActorRef}

object EventSource {
  case class RegisterListener(listener: ActorRef)
  case class UnregisterListener(listener: ActorRef)
}

trait EventSource { this: Actor =>

  import EventSource._

  var listeners = Vector.empty[ActorRef]

  def sendEvents[T](event: T): Unit = {
    listeners foreach (_ ! event)
  }

  def eventSourceRecevive: Receive = {
    case RegisterListener(listener) =>
      listeners = listeners :+ listener
    case UnregisterListener(listener) =>
      listeners = listeners filter (_ != listener)
  }
}
