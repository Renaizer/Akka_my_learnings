package example

import akka.actor.{Props, ActorRef, Actor, ActorSystem}
import akka.pattern.ask
import scala.concurrent.Await
import akka.util.Timeout
import scala.concurrent.duration._
import ControlSurface._

import scala.concurrent.ExecutionContext.Implicits.global

object Avionics extends App{
  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("PlaneSimulation")
  val plane = system.actorOf(Props[Plane], "Plane")

  val control = Await.result((plane ? Plane.GiveMeControl).mapTo[ActorRef], 5.seconds)
  system.scheduler.scheduleOnce(200.millis){
    control ! StickBack(1f)
  }

  system.scheduler.scheduleOnce(1.seconds){
    control ! StickBack(0f)
  }

  system.scheduler.scheduleOnce(2.seconds){
    control ! StickBack(0.5f)
  }

  system.scheduler.scheduleOnce(3.seconds){
    control ! StickBack(0f)
  }

  system.scheduler.scheduleOnce(5.seconds) {
    system.terminate()
  }
}
