package logan.core

import akka.actor.{ActorRef, Props}
import logan.actors.StdoutActor

object Stdout {
  implicit class StdoutFromTransform(val source: Transform[_, String]) {
    def stdout() = new Stdout(source.context, source)
  }

  implicit class StdoutFromSource(val source: Source[String]) {
    def stdout() = new Stdout(source.context, source)
  }
}

class Stdout(context: Context, source: Node)
  extends Sink[String](context, source) {

  override def actorProps(consumerActors: Seq[ActorRef]) =
    Props(classOf[StdoutActor], this, consumerActors)
}
