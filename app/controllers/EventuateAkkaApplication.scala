package controllers

import javax.inject.{Singleton, Inject}

import akka.actor.ActorSystem
import akka.util.Timeout
import play.api.mvc._
import services.HelloActor
import services.HelloActor.SayHello
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import akka.pattern.ask
import scala.concurrent.duration._


@Singleton
class EventuateAkkaApplication @Inject() (system: ActorSystem) extends Controller{

  val helloActor = system.actorOf(HelloActor.props, "hello-actor")

  implicit val timeout = Timeout(5.seconds)

  def sayHello(name: String) = Action.async {
    (helloActor ? SayHello(name)).mapTo[String].map { message =>
      Ok(message)
    }
  }

}
