package example


import akka.actor.{ActorRef, Actor}
object EventSource {
  case class RegisterListener(listener:ActorRef)
  case class unregisterListener(listener:ActorRef)
}


trait EventSource {
  this : Actor =>
  import EventSource._

  var listeners = Vector.empty[ActorRef]

  def sendEvent[T](event:T) = listeners foreach (_ ! event)

  def eventSourceReceive : Receive ={
    case RegisterListener(listener) => listeners = listeners :+ listener
    case unregisterListener(listener) => listeners = listeners filter (_ != listener)
  }
}


