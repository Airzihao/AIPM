import java.io.{File, FileInputStream}
import java.util.Properties

import org.apache.commons.io.IOUtils
import org.grapheco.aipm.common.utils.Logging

import scala.collection.JavaConversions

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:08 2020/9/30
 * @Modified By:
 */
object PMServer extends Logging {
  val logo = IOUtils.toString(this.getClass.getClassLoader.getResourceAsStream("logo.txt"), "utf-8");

  def startServer(propFile: File, overrided: Map[String, String] = Map()): PMServer = {
    val props = new Properties()
    props.load(new FileInputStream(propFile))

    val server = new PMServer(JavaConversions.propertiesAsScalaMap(props).toMap ++ overrided)
    server.start()
    server
  }
}
class PMServer(confMap: Map[String, Any]) {

  def start(): Unit ={
    _initGlobalContext()
    println(PMServer.logo)
  }
  private def _initGlobalContext() {
    confMap.foreach(pair => {
      pair._1 match {
        case "containerId" => GlobalContext.setContainerId(pair._2.toString)
        case "serverIp" => GlobalContext.setServerIp(pair._2.toString)
        case "aipmRpcPort" => GlobalContext.setRpcPort(pair._2.toString)
        case "dockerAPIUrl" => GlobalContext.setDockerAPIUrl(pair._2.toString)
        case _ => println(s"Not uesd: $pair")
      }
    })
  }

}
