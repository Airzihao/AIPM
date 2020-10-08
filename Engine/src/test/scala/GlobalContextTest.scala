import java.io.{File, FileInputStream}
import java.util.Properties

import org.grapheco.aipm.common.utils.{GlobalContext, WrongArgsException}
import org.junit.{Assert, Test}

import scala.collection.JavaConversions
import scala.collection.immutable.HashMap

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:27 2020/10/1
 * @Modified By:
 */
class GlobalContextTest {

  @Test
  def argsFromFile(): Unit ={
    val overrided: Map[String, String] = HashMap[String, String]()
    val propFile: File = new File("./src/test/resources/aipm.properties")
    val server = PMServer.startServer(propFile, overrided)
    val props = new Properties()
    props.load(new FileInputStream(propFile))
    val propMap = JavaConversions.propertiesAsScalaMap(props).toMap
    propMap.foreach(pair => {
      Assert.assertEquals(pair._2, GlobalContext.get[String](pair._1))
    })
  }

  @Test
  def wrongArgs(): Unit = {
    val wIp = List("10.0.256.9", "499.499.255.255")
    val wRpcPort = List("0", "9999999", "12")
    val wDockerAPIUrl = List("10.0.0.1", "10.0.0.1:1234","tcp:10.0.0.1:2375")
    val wAIPMRpcServerIP = List("10.0.88.88", "10.0.99.99:", "10.0.88.88,10.0.99.99")

    wrongArgsListTest(wIp, ipTest)
    wrongArgsListTest(wRpcPort, portTest)
    wrongArgsListTest(wDockerAPIUrl, dockerAPIUrlTest)
    wrongArgsListTest(wAIPMRpcServerIP, aipmRpcServerIpTest)
  }


  def wrongArgsListTest(list: List[String], f: (String) => Unit): Unit = {
    list.foreach(item => {
      try {
        f(item)
        Assert.assertTrue(false)
      } catch {
        case ex: WrongArgsException => {
          Assert.assertTrue(true)
        }
        case _ => {
          Assert.assertTrue(false)
        }
      }
    })
  }

  val ipTest = (ip: String) => GlobalContext.setServerIp(ip)
  val portTest = (port: String) => GlobalContext.setRpcPort(port)
  val dockerAPIUrlTest = (url: String) => GlobalContext.setDockerAPIUrl(url)
  val aipmRpcServerIpTest = (ipStr: String) => GlobalContext.setAIPMRpcServerIp(ipStr)
}
