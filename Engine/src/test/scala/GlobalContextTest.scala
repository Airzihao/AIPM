import java.io.{File, FileInputStream}
import java.util.Properties

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



  // contextMap Test
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
    val wrongIp = List("10.0.256.9", "499.499.255.255")
    val wrongRpcPort = List("0", "9999999", "12")
    wrongIp.foreach(item => {
      try {
        GlobalContext.setServerIp(item)
        Assert.assertTrue(false)
      } catch {
        case ex: WrongArgsException => {
          Assert.assertTrue(true)
        }
        case _ => {

          Assert.assertTrue(false)
          println(item)
        }
      }
    })


    wrongRpcPort.foreach(item => {
      try {
        GlobalContext.serRpcPort(item)
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





}
