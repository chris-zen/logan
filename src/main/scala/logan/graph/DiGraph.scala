package logan.graph

class DiGraph[N](val nodes: Seq[N], adj: N => Iterator[N]) {

  def this(nodes: Seq[N], adjLists: Map[N, Set[N]]) = {
    this(nodes, n => adjLists(n).iterator)
  }

  def numVertices = nodes.size

  def adjacency(node: N): Iterator[N] = adj(node)

  def reverse(): DiGraph[N] = {
    var adjacencies: Map[N, Set[N]] = Map().withDefaultValue(Set.empty)
    for {
      node <- nodes
      adjacentNode <- adjacency(node)
    } adjacencies += (adjacentNode -> (adjacencies(adjacentNode) + node))

    new DiGraph[N](nodes, adjacencies)
  }

  override def toString: String = {
    var sb: List[String] = Nil
    for (node <- nodes) {
      sb = sb :+ node.toString + " -> " + adj(node).map(_.toString).mkString(", ") + "\n"
    }
    sb.mkString("")
  }
}
