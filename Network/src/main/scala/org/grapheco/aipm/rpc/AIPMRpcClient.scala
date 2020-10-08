package org.grapheco.aipm.rpc

import com.google.gson.reflect.TypeToken
import com.google.protobuf.ByteString
import io.grpc.ManagedChannel
import org.grapheco.aipm.common.utils.{AIPMRpcError, GlobalContext, Logging, WrongArgsException}
import com.google.gson.{Gson, JsonArray, JsonObject, JsonParser}
import scala.util.parsing.json.JSON

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 19:14 2020/10/7
 * @Modified By:
 */
class AIPMRpcClient(val rpcServerIp: String = GlobalContext.getAIPMRpcServerIp()) extends Logging{


  val channel = _getChannel()
  val stub = _getBlockingStub()
  private def _getChannel(): ManagedChannel = {
    // fixme: why it is red????
    io.grpc.ManagedChannelBuilder.forTarget(rpcServerIp).usePlaintext().build();
  }
  private def _getBlockingStub(): FaceFeatureApiGrpc.FaceFeatureApiBlockingStub = {
    FaceFeatureApiGrpc.newBlockingStub(channel)
  }

// fixme: don't delete it, maybe useful in the future.
//  private def _wrapResult[T](jsonStr: String, fieldName: String): T = {
//    val jsonObj: JsonObject = new JsonParser().parse(jsonStr).asInstanceOf[JsonObject]
//    if (!jsonObj.has(fieldName)) {
//      logger.error(s"No $fieldName in the result.\n The result is: $jsonStr")
//      throw new WrongArgsException(s"No $fieldName in the result.")
//    }
//    val _jsonElement = jsonObj.get(fieldName)
//    val result: T = _jsonElement match {
//      case _j: JsonArray => new Gson().fromJson(_jsonElement, new TypeToken[T](){}.getType)
//    }
//    result.asInstanceOf[T]
//  }

}
