package logan.core

import akka.actor.{ActorRef, Props}
import logan.actors.FromFileActor

class FromFile(context: Context, val path: String)
  extends Source[String](context) {

  def actorProps(consumerActors: Seq[ActorRef]) =
    Props(classOf[FromFileActor], this, consumerActors)
}
