package example

import akka.actor.{Actor, ActorLogging, Props}

object Plane{
  case object GiveMeControl
}

class Plane extends Actor with ActorLogging{
  import Altimeter._
  import ControlSurface._
  import Plane._
  import EventSource._

  val altimeter = context.actorOf(Props[Altimeter], "Altimeter")
  val controls  = context.actorOf(Props(new ControlSurface(altimeter)), "ControlSurface")

  override def preStart(): Unit = {
    altimeter ! RegisterListener(self)
  }

  def receive:Receive={
    case GiveMeControl =>
                      log.info(s"Plane giving the controls")
                      sender ! controls
    case AltitudeUpdate(altitude) =>
                      log.info(s"Altitude is now $altitude")
  }

}
