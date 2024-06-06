package example

import akka.actor.{Actor, ActorRef}

object ControlSurface{
  case class StickBack(amount:Float)
  case class Stickforward(amount:Float)
}


class ControlSurface(altimeter:ActorRef) extends Actor{
  import ControlSurface._
  import Altimeter._

def receive:Receive = {
  case StickBack(amount) => altimeter ! RateChange(amount)

  case Stickforward(amount) => altimeter ! RateChange(-1 * amount)
}

}
