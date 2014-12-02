package logan.core

abstract class Sink[T](context: Context, sources: Seq[Node])
  extends Node(context, sources) {

  def this(context: Context) = this(context, List())
  def this(context: Context, source: Node) = this(context, List(source))

}
