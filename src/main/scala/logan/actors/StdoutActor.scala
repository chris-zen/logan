package logan.actors

import akka.actor.ActorRef
import akka.event.LoggingReceive
import logan.core.Stdout

class StdoutActor (node: Stdout, consumers: Seq[ActorRef])
  extends NodeActor(node, consumers) {

  def receive = {
    LoggingReceive {
      case msg =>
        println(">>> " + msg)
        //sender ! RequestMore
    }
  }
}
