package example



import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef}
object EventSource {
  case class RegisterListener(listener:ActorRef)
  case class UnregisterListener(listener:ActorRef)
}

trait EventSource {
  def sendEvent[T](event:T):Unit
  def eventSourceReceive : Receive
}

trait productionEventSource extends EventSource{
  this : Actor =>
  import EventSource._

  var listeners = Vector.empty[ActorRef]

  override def sendEvent[T](event:T):Unit = listeners foreach (_ ! event)

  override def eventSourceReceive : Receive ={
    case RegisterListener(listener) => listeners = listeners :+ listener
    case UnregisterListener(listener) => listeners = listeners filter (_ != listener)
  }
}


