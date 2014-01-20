package avionics

import akka.actor.{ActorRef, Actor}

trait ProductionEventSource extends EventSource { this: Actor =>

  import EventSource._

  var listeners = Vector.empty[ActorRef]

  override def sendEvent[T](event: T): Unit = {
    listeners foreach (_ ! event)
  }

  override def eventSourceReceive: Receive = {
    case RegisterListener(listener) =>
      listeners = listeners :+ listener
    case UnregisterListener(listener) =>
      listeners = listeners filter (_ != listener)
  }
}
