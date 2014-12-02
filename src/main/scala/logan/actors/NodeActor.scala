package logan.actors

import akka.actor.{ActorLogging, ActorRef, Actor}
import logan.core.Node

abstract class NodeActor(val node: Node, val consumers: Seq[ActorRef])
  extends Actor with ActorLogging {

}
