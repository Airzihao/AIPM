package org.grapheco.aipm.common.utils

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 19:38 2020/10/6
 * @Modified By:
 */
object ArgsFormatChecker {

  val ipPattern = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)".r
  val aipmRpcPort = "^\\d{3,5}$".r
  val dockerAPIUrlPattern = "tcp://((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?):[0-9]{1,5}".r

  def isValid(arg: Any, formatName: String): Boolean = {
    formatName match {
      case "serverIp" => ipPattern.pattern.matcher(arg.toString).matches()
      case "aipmRpcPort" =>  aipmRpcPort.pattern.matcher(arg.toString).matches()
      case "dockerAPIUrlPattern" =>  dockerAPIUrlPattern.pattern.matcher(arg.toString).matches()
      case _ => false
    }
  }

}
