package org.grapheco.aipm.common.utils

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:46 2020/10/6
 * @Modified By:
 */
case class NodeAddress(host: String, port: Int) {
  def getAsString: String = {
    host + ":" + port.toString
  }
}

object NodeAddress {
  def fromString(url: String, separator: String = ":"): NodeAddress = {
    val pair = url.split(separator)
    NodeAddress(pair(0), pair(1).toInt)
  }
}
