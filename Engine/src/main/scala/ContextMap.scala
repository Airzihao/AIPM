import org.grapheco.aipm.common.utils.{ArgsFormatChecker, WrongArgsException}

import scala.collection.Set
import scala.collection.mutable.{Map => MMap}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:37 2020/9/30
 * @Modified By:
 */
class ContextMap {

  private val _map: MMap[String, Any] = MMap[String, Any]()

  def keys: Set[String] =_map.keySet;

  protected def put[T](key: String, value: T): T = {
    _map(key) = value
    value
  }
  protected def put[T](value: T)(implicit manifest: Manifest[T]): T = put[T](manifest.runtimeClass.getName, value)

  def get[T](key: String): T = {
    try {
      _map(key).asInstanceOf[T]
    } catch {
      case ex: Exception => {
        throw new WrongArgsException(s"Not found $key in GlobalContext.")
      }
    }
  }

}

object GlobalContext extends ContextMap {

  def setContainerId(id: String): Unit = {
    put("containerId", id)
  }
  def getContainerId(): String = {
    get[String]("containerId")
  }
  def setServerIp(ip: String): Unit = {
    if(ArgsFormatChecker.isValid(ip, "serverIp")){
      put("serverIp", ip)
    } else {
      throw new WrongArgsException(s"Wrong server ip value for $ip")
    }
  }

  def getServerIp(): String = {
    get[String]("serverIp")
  }

  def setRpcPort(port: String): Unit = {
    if(ArgsFormatChecker.isValid(port, "aipmRpcPort")) {
      put("aipmRpcPort", port)
    } else {
      throw new WrongArgsException(s"Wrong aipmRpcPort value for $port")
    }
  }

  def getRpcPort(): String = {
    get[String]("aipmRpcPort")
  }

  def setDockerAPIUrl(url: String): Unit = {
    if(ArgsFormatChecker.isValid(url, "dockerAPIUrl")) {
      put("dockerAPIUrl", url)
    } else {
      throw new WrongArgsException(s"Wrong dockerAPIUrl for $url")
    }
  }

  def getDockerAPIUrl(): String = {
    get[String]("dockerAPIUrl")
  }
}
