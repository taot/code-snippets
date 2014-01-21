package avionics

import akka.actor.{Props, ActorRef, Actor}

trait AttendantCreationPolicy {
  val numberOfAttendants: Int = 8
  def createAttendant: Actor = FlightAttendant()
}

trait LeadFlightAttendantProvider {
  def newFlightAttendant: Actor = LeadFlightAttendant()
}

object LeadFlightAttendant {
  case object GetFlightAttendant
  case class Attendant(a: ActorRef)

  def apply() = new LeadFlightAttendant with AttendantCreationPolicy
}

class LeadFlightAttendant extends Actor { this: AttendantCreationPolicy =>

  import LeadFlightAttendant._

  override def preStart() {
    import scala.collection.JavaConverters._
    val attendantNames = context.system.settings.config.getStringList("avionics.flightcrew.attendantNames").asScala
    attendantNames take numberOfAttendants foreach { i =>
      context.actorOf(Props(createAttendant), i)
    }
  }

  def randomAttendant(): ActorRef = {
    context.children.take(scala.util.Random.nextInt(numberOfAttendants) + 1).last
  }

  def receive = {
    case GetFlightAttendant =>
      sender ! randomAttendant()
    case m =>
      randomAttendant() forward m
  }
}
