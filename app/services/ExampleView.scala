package services

import scala.util._
import akka.actor._
import com.rbmhtechnology.eventuate.EventsourcedActor



// Commands
case object Print
case class Append(entry: String)

// Command replies
case class AppendSuccess(entry: String)
case class AppendFailure(cause: Throwable)

// Event
case class Appended(entry: String)


class ExampleActor(override val id: String,
                   override val aggregateId: Option[String],
                   override val eventLog: ActorRef) extends EventsourcedActor {

  private var currentState: Vector[String] = Vector.empty

  override val onCommand: Receive = {
    case Print =>
      println(s"[id = $id, aggregate id = ${aggregateId.getOrElse("<undefined>")}] ${currentState.mkString(",")}")
    case Append(entry) => persist(Appended(entry)) {
      case Success(evt) =>
        onEvent(evt)
        sender() ! "Append Successful :"+currentState.toString()
      case Failure(err) =>
        sender() ! err.toString
    }
    case _ =>
      sender() ! "unidentified :" + onCommand
  }

  override val onEvent: Receive = {
    case Appended(entry) => currentState = currentState :+ entry
  }
}