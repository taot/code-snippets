package avionics

import akka.actor.{ActorLogging, Props, Actor}

object Plane {
  case object GiveMeControl
}

class Plane extends Actor with ActorLogging {

  import Plane._
  import EventSource._
  import Altimeter._

  val altimeter = context.actorOf(Props[Altimeter])
//  val controls = context.actorOf(Props(classOf[ControlSurfaces], altimeter))
  val controls = context.actorOf(Props[ControlSurfaces](new ControlSurfaces(altimeter)))

  override def preStart(): Unit = {
    super.preStart()
    altimeter ! RegisterListener(self)
  }

  def receive = {
    case GiveMeControl =>
      log.info("Plane giving control.")
      sender ! controls
    case AltitudeUpdate(altitude) =>
      log.info("Altitude is now " + altitude)
  }
}