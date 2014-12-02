package logan.core

class Mapper[S, T](context: Context, source: Node, f: S => T)
  extends Transform[S, T](context, source) {

  def compute(data: S): Option[T] = {
    Some(f(data))
  }
}
