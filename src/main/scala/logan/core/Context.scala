package logan.core

import akka.actor.{ActorSystem, Props}
import logan.actors.ContextSupervisor
import org.streum.configrity.Configuration

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration

object Context {

  def apply() = new Context(Configuration())

  def apply(extConfig: Configuration) = new Context(extConfig)

}

class Context(extConfig: Configuration) {

  private val config = extConfig include Configuration(
    "name" -> "Ctx"
  )

  private[logan] val nodes = new ArrayBuffer[Node]()

  private var nodeNameCounts: Map[String, Int] = Map().withDefaultValue(0)

  private def genNodeId(node: Node): String = {
    val nodeName = node.getClass.getSimpleName.toLowerCase
    val count = nodeNameCounts(nodeName) + 1
    nodeNameCounts += (nodeName -> count)
    nodeName + count.toString
  }

  private[logan] def addNode(node: Node): String = {
    nodes += node
    genNodeId(node)
  }

  def stdin() = {
    new Stdin(this)
  }

  def fromFile(path: String) = {
    new FromFile(this, path)
  }

  private val name = config[String]("name")
  private val system = ActorSystem(name)
  private val supervisor = system.actorOf(Props(classOf[ContextSupervisor], this), "supervisor")

  private var running = false

  def start(): Unit = {
    this.synchronized {
      if (running)
        throw new IllegalStateException("Context already running")

      running = true
      supervisor ! ContextSupervisor.MaterializeGraph
    }
  }

  def isRunning = !system.isTerminated

  def wait(timeout: Duration) = {
    this.synchronized {
      system.awaitTermination(timeout)
    }
  }

  def stop(): Unit = {
    this.synchronized {
      system.shutdown()
      system.awaitTermination()
      running = false
    }
  }
}
