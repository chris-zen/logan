package logan.core

class Filter[T](context: Context, source: Node, predicate: T => Boolean)
  extends Transform[T, T](context, source) {

  override def compute(data: T): Option[T] = {
    if (predicate(data))
      Some(data)
    else
      None
  }
}
