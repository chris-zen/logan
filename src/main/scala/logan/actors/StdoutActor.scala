package logan.actors

import akka.actor.ActorRef
import logan.core.{Stdout, FromFile}

class StdoutActor (node: Stdout, consumers: Seq[ActorRef])
  extends NodeActor(node, consumers) {

  def receive = {
    case msg =>
      println(msg)
      // msg.sender ! ReadMore
  }
}
