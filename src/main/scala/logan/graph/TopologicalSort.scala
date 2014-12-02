package logan.graph

class TopologicalSort[N](g: DiGraph[N]) {

  var marked: Set[N] = Set.empty
  var reversePost: List[N] = Nil

  // TODO check acyclic

  private def dfs(node: N): Unit = {
    marked = marked + node
    g.adjacency(node).filter(!marked.contains(_)).foreach(dfs(_))
    reversePost = node :: reversePost
  }

  def order(): Iterator[N] = {
    g.nodes.filter(!marked.contains(_)).foreach(dfs(_))
    reversePost.iterator
  }
}
