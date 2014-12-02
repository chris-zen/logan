logan
=====

Logs analysis and metrics using principles of data-flows.

The principal idea is to define typed data-flows taking advantage of the flexibility of the Scala language as a DSL for the purpose of taking logs from several sources, transforming the logs into metrics and react to them throwing alerts or simply storing them into a database.

The underlaying implementation transforms the data-flow graph into an Akka ActorSystem. The sources, transforming operations and sinks are represented with actors, and the logs/metrics as typed messages which flow through them.

This is a hobby project, neither the name of the project (do a query in github for logan) nor the principal idea are brand new here. I get inspiration from other mature projects such as [logstash](http://logstash.net/), [Spark Streaming](https://spark.apache.org/streaming/), [RxJava](https://github.com/ReactiveX/RxJava), [Reactive Streams](http://www.reactive-streams.org/), and so forth. It is just that I want to understand them better and experiment by myself some of the solutions (specialy the Spark approach for defining data-flows), and of course, learning Scala.

I always look for an snippet of code when looking at a project so here it is a very simple example of how I pretend to be able to define data-flows and do all these amazing things with logs:

```Scala
package test

import org.streum.configrity.Configuration

import logan.core.Stdout._
import logan.core.InfluxDB._

object Example1 {

  val config = Configuration(
    "influxdb.host" -> "localhost",
    "influxdb.port" -> 8083,
    "influxdb.user" -> "root",
    "influxdb.password" -> "root"
  )
  
  val ctx = Context(config)

  val stdin = ctx.stdin()

  stdin
    .filter(_.startsWith("INFO"))
    .stdout()

  stdin
    .filter(_.startsWith("ERROR"))
    .map(log => 1)
    .toInfluxdb("series1", "col1")

  def main(args: Array[String]): Unit = {
  
    ctx.start()

    Thread.sleep(10000)

    ctx.stop()
  }
}
```

I have in mind adding several operations such as union, grouping by key, grouping by time, mean, median, stdev, percentile, ... and some more sources and sinks such as fromTail, fromFile and toFile.
