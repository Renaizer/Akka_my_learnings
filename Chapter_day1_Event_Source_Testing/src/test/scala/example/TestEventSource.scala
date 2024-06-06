package example

import akka.actor.{Actor, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class TestEventSource extends Actor with productionEventSource {
 def receive:Receive = eventSourceReceive
}

class eventSourceSpec extends TestKit(ActorSystem("EventSourceSpec")) with WordSpecLike with MustMatchers with BeforeAndAfterAll
{
  import EventSource._

  override def afterAll(): Unit = system.terminate()

  "Event Source" should{
    "Allow us to register a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.listeners must contain (testActor)
    }

    "Allow us to unregister the listeners" in{
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.receive(UnregisterListener(testActor))
      real.listeners.size must be (0)
    }

    "send the event to the test Actor" in{
      val testA = TestActorRef[TestEventSource]
      testA ! RegisterListener(testActor)
      testA.underlyingActor.sendEvent("sample string event")
      expectMsg("sample string event")
    }
  }
}
