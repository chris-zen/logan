package logan.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import logan.actors.ContextSupervisor.MaterializeGraph
import logan.core.{Context, Node}
import logan.graph.{DiGraph, TopologicalSort}

object ContextSupervisor {

  case class MaterializeGraph()

}

class ContextSupervisor(loganContext: Context) extends Actor with ActorLogging {

  var actorsByNode: Map[String, ActorRef] = Map()

  def receive = {
    case MaterializeGraph => materializeGraph
  }

  def materializeGraph = {
    log.info("Materializing graph ...")

    val depsGraph = new DiGraph[Node](loganContext.nodes, { node => node.sources.iterator })
    val flowGraph = depsGraph.reverse()
    log.debug("DataFlow:\n" + flowGraph.toString)

    val order = new TopologicalSort[Node](depsGraph).order()

    for (node <- order) {
      val consumers = flowGraph.adjacency(node).toSeq
      val consumerActors = for (consumer <- consumers) yield actorsByNode(consumer.id)

      log.debug("Creating actor for node {}{}", node.id,
        if (consumers.size > 0) " with consumers " + consumers.mkString(", ") else "")

      val actorRef = context.actorOf(node.actorProps(consumerActors), node.id)

      actorsByNode += (node.id -> actorRef)
    }
  }
}
