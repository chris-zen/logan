package logan.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import logan.core.Node

case class RequestMore()

abstract class NodeActor(val node: Node, val parents: Seq[ActorRef], val consumers: Seq[ActorRef])
  extends Actor with ActorLogging {

}
