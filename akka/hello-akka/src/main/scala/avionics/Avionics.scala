package avionics

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.util.Timeout
import akka.actor.{ActorRef, Props, ActorSystem}
import scala.concurrent.Await
import avionics.Plane.GiveMeControl
import akka.pattern.ask

object Avionics {

  implicit val timeout = Timeout(5.seconds)

  val system = ActorSystem("PlaneSimulation")

  val plane = system.actorOf(Props[Plane], "Plane")

  def main(args: Array[String]): Unit = {
    val control = Await.result(
      (plane ? GiveMeControl).mapTo[ActorRef],
      5.second
    )
    system.scheduler.scheduleOnce(200.millis) {
      control ! ControlSurfaces.StickBack(1f)
    }
    system.scheduler.scheduleOnce(1.seconds) {
      control ! ControlSurfaces.StickBack(0f)
    }
    system.scheduler.scheduleOnce(3.seconds) {
      control ! ControlSurfaces.StickBack(0.5f)
    }
    system.scheduler.scheduleOnce(4.seconds) {
      control ! ControlSurfaces.StickBack(0f)
    }
    system.scheduler.scheduleOnce(5.seconds) {
      system.shutdown()
    }
  }
}
