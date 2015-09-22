package services

/**
 * Created by naveen on 20/9/15.
 */
import akka.actor.ActorRef
import com.rbmhtechnology.eventuate.EventsourcedView
import com.rbmhtechnology.eventuate.VectorTime
import play.api.Logger

case class Resolved(selectedTimestamp: VectorTime)

case object GetAppendCount
case class GetAppendCountReply(count: Long)

case object GetResolveCount
case class GetResolveCountReply(count: Long)

class ExampleActorView(override val id: String, override val eventLog: ActorRef) extends EventsourcedView {
  private var appendCount: Long = 0L
  private var resolveCount: Long = 0L

  override val onCommand: Receive = {
    case GetAppendCount => sender() ! appendCount.toString
    case GetResolveCount => sender() ! resolveCount.toString
    case _ => sender() ! "UnIdentifed"
  }

  override val onEvent: Receive = {
    case Appended(_) => {
      Logger.info("increasing append count from :"+appendCount)
      appendCount += 1L
    }
    case Resolved(_) => resolveCount += 1L
  }
}