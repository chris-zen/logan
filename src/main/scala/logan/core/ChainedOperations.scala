package logan.core

trait ChainedOperations[S, T] {
  this: Node =>

  def filter(predicate: T => Boolean) = new Filter[T](context, this, predicate)

  def map[T](f: S => T) = new Mapper[S, T](context, this, f)
}
