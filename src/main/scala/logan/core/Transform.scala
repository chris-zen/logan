package logan.core

import akka.actor.{ActorRef, Props}
import logan.actors.TransformActor

abstract class Transform[S, T](context: Context, sources: Seq[Node])
  extends Node(context, sources) with ChainedOperations[S, T] {

  def this(context: Context) = this(context, List())
  def this(context: Context, source: Node) = this(context, List(source))

  def actorProps(consumerActors: Seq[ActorRef]) =
    Props(classOf[TransformActor[S, T]], this, consumerActors)

  private[logan] def compute(data: S): Option[T]
}
