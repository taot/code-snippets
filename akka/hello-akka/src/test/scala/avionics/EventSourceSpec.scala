package avionics

import akka.actor.{ActorSystem, Actor}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{WordSpecLike, BeforeAndAfterAll, MustMatchers}

class TestEventSource extends Actor with ProductionEventSource {

  def receive = eventSourceReceive
}

class EventSourceSpec extends TestKit(ActorSystem("EventSourceSpec")) with WordSpecLike with MustMatchers with BeforeAndAfterAll with ImplicitSender {

  import EventSource._

  override def afterAll() {
    system.shutdown()
  }

  "EventSource" should {

    "allow us to register a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.listeners must contain (testActor)
    }

    "allow us to unregister a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.receive(UnregisterListener(testActor))
      real.listeners.size must be (0)
    }

    "send the event to out test actor" in {
      val testA = TestActorRef[TestEventSource]
      testA ! RegisterListener(testActor)
      testA.underlyingActor.sendEvent("Fibonacci")
      expectMsg("Fibonacci")
    }
  }

}
