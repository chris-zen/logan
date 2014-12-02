package logan.core

import akka.actor.{Props, ActorRef}

abstract class Node(
  val context: Context,
  private[logan] val sources: Seq[Node]) {

  // No sources
  def this(context: Context) = this(context, List())

  // Single source
  def this(context: Context, source: Node) = this(context, List(source))

  def actorProps(consumers: Seq[ActorRef]): Props

  val id = context.addNode(this)

  override def toString = id

}
