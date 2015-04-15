package logan.core

import akka.actor.{ActorRef, Props}
import logan.actors.StdinActor

class Stdin(context: Context)
  extends Source[String](context) {

  def actorProps(consumerActors: Seq[ActorRef]) =
    Props(classOf[StdinActor], this, consumerActors)
}
