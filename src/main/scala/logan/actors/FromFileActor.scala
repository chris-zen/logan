package logan.actors

import akka.actor.ActorRef
import logan.core.FromFile

class FromFileActor (node: FromFile, consumers: Seq[ActorRef])
  extends NodeActor(node, consumers) {

  def receive = {
    case _ =>
  }
}
