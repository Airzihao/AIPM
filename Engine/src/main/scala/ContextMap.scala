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

//  def init(propMap: Map[String, Any]): Unit ={
//    propMap.map(pair => put(pair._1, pair._2))
//  }

}

object GlobalContext extends ContextMap {

  def setContainerId(id: String): Unit = {
    put("containerId", id)
  }
  def getContainerId(): String = {
    get[String]("containerId")
  }

  def setServerIp(ip: String): Unit = {
    val ipPattern = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)".r
    val ipPatternWithPort = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?):[0-9]{1,5}".r
    val vaildIp = ipPattern findFirstIn(ip)
    if (vaildIp.nonEmpty) {
      put("serverIp", vaildIp.get)
    } else {
      throw new WrongArgsException(s"Wrong server ip value for $ip")
    }
  }
  def getServerIp(): String = {
    get[String]("serverIp")
  }

  def serRpcPort(port: String): Unit = {
    val portPattern = "^\\d{3,5}$".r
    val vaildPort = portPattern findFirstIn(port)
    if(vaildPort.nonEmpty) {
      put("aipmRpcPort", vaildPort.get)
    } else {
      throw new WrongArgsException(s"Wrong aipmRpcPort value for $port")
    }
  }
  def getRpcPort(): String = {
    get[String]("aipmRpcPort")
  }
}
