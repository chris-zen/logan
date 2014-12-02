package logan.actors

import akka.actor.ActorRef
import logan.core.Transform

class TransformActor[S, T](node: Transform[S, T], consumers: Seq[ActorRef])
  extends NodeActor(node, consumers) {

  def receive = {
    case msg =>
      log.debug(msg.toString)
      val result = node.compute(msg.asInstanceOf[S])
      for (consumer <- consumers)
        consumer ! result
  }
}
