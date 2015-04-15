package logan

import logan.core.Context
import logan.core.Stdout._
import org.streum.configrity.Configuration

object GraphTest {

  val errorPattern = "ERROR .*".r.pattern
  
  val config = Configuration(
    "influxdb.host" -> "localhost",
    "influxdb.port" -> 8083,
    "influxdb.user" -> "root",
    "influxdb.password" -> "root"
  )

  val ctx = Context(config)

  val stdin = ctx.stdin()

  stdin.stdout()

  stdin
    .filter(_.startsWith("INFO"))
    .stdout()

  stdin
    .filter(errorPattern.matcher(_).matches())
    .map(log => "1")
    .stdout()
    //.influxdb("serie1", "col1")

  def main(args: Array[String]): Unit = {

    ctx.start()

    Thread.sleep(1000000)

    ctx.stop()
  }
}
