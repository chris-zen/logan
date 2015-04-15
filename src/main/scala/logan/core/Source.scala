package logan.core

abstract class Source[T](context: Context, sources: Seq[Node])
  extends Node(context, sources) with ChainedOperations[T, T] {

  def this(context: Context) = this(context, List())
  def this(context: Context, source: Node) = this(context, List(source))
}
