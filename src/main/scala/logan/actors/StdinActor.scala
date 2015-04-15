package logan.actors

import akka.actor.ActorRef
import akka.pattern.pipe
import logan.core.Node

import scala.concurrent.Future

class StdinActor(node: Node, consumers: Seq[ActorRef])
  extends NodeActor(node, consumers) {

  case class ReadLine()
  case class SendLine(line: String)

  override def preStart(): Unit = {
    self ! ReadLine
  }

  implicit val ec = context.dispatcher

  def receive = {
    case RequestMore =>
      self ! ReadLine

    case ReadLine =>
      Future {
        SendLine(Console.in.readLine())
      } pipeTo self

    case SendLine(line) =>
      consumers.foreach({ s => s ! line})
      self ! ReadLine
  }
}
