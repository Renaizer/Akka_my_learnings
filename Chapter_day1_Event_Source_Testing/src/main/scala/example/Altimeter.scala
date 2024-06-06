package example

import akka.actor.{Actor, ActorLogging}
import scala.concurrent.duration._

object Altimeter{
  case class RateChange( amount:Float )
  case class AltitudeUpdate( altitude:Double )

  def apply() = new Altimeter with productionEventSource
}


class Altimeter extends Actor with ActorLogging{
  this:EventSource =>

  import Altimeter._
  implicit val ec = context.dispatcher
  val maxRateOfClimb = 5000
  var rateofClimb = 0f
  var altitude:Double = 0f
  var lastTick:Double = System.currentTimeMillis
  case object Tick

  val ticker = context.system.scheduler.schedule(100.millis,100.millis,self,Tick)

  def altimeterReceive:Receive={
    case RateChange(amount) =>
                rateofClimb = amount.min(1.0f).max(-1.0f) * maxRateOfClimb
                log.info(s"Altimeter changed rate of climb to $rateofClimb")

    case Tick =>
                val tick:Double = System.currentTimeMillis()
                altitude  = altitude + ((tick - lastTick)/60000) * rateofClimb
                sendEvent(AltitudeUpdate(altitude))
  }

  def receive:Receive = eventSourceReceive orElse altimeterReceive
}
