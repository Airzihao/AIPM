package org.grapheco.aipm.common.utils

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:45 2020/10/1
 * @Modified By:
 */
class AipmException(msg: String) extends Exception(msg) {

}

class WrongArgsException(msg: String) extends Exception(msg) {

}

class RpcConnectionError(msg: String) extends Exception(msg) {

}

class AipmRpcError(msg: String) extends Exception(msg) {

}