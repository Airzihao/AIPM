package org.grapheco.aipm.rpc

import com.google.protobuf.ByteString
import io.grpc.ManagedChannel
import org.grapheco.aipm.common.utils.{AipmRpcError, GlobalContext, Logging, WrongArgsException}

import scala.collection.immutable.Map
import scala.util.parsing.json.JSON

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 22:10 2020/10/8
 * @Modified By:
 */

class FaceFeatureClient(val rpcServerIp: String = GlobalContext.getAipmRpcServerIp()) extends AipmRpcClient {

  protected val _channel: ManagedChannel = io.grpc.ManagedChannelBuilder.forTarget(rpcServerIp).usePlaintext().build();

  val stub = _getBlockingStub()

  def getFaceFeatures(arg: Any): List[List[Double]] = {
    try {
      val response: FaceFeatureService.FaceFeatureResponse = {
        arg match {
          case arg: String => {
            val urlRequest: FaceFeatureService.UrlFaceFeatureRequest =
              FaceFeatureService.UrlFaceFeatureRequest.newBuilder().setImgUrl(arg.asInstanceOf[String]).build()
            val urlResponse: FaceFeatureService.FaceFeatureResponse = stub.getUrlFaceFeature(urlRequest)
            urlResponse
          }
          case arg: ByteString => {
            val bytesRequest: FaceFeatureService.BytesFaceFeatureRequest =
              FaceFeatureService.BytesFaceFeatureRequest.newBuilder().setImgBytes(arg.asInstanceOf[ByteString]).build()
            val bytesResponse: FaceFeatureService.FaceFeatureResponse = stub.getBytesFaceFeature(bytesRequest)
            bytesResponse
          }
          case _ => {
            throw new WrongArgsException(s"Wrong arg type for $arg in getFaceFeatures.")
          }
        }
      }
      _wrapResult(response.getJsonResultBytes.toStringUtf8)
    } catch {
      case ex: Exception => {
        logger.error(ex.getMessage)
        throw new AipmRpcError(ex.getMessage)
      }
    }
  }

  private def _wrapResult(jsonStr: String): List[List[Double]] = {
    val json: Option[Any] = JSON.parseFull(jsonStr)
    val map: Map[String, Any] = json.get.asInstanceOf[Map[String, Any]]
    if (map("res").asInstanceOf[Boolean]) {
      if(map("value").asInstanceOf[List[List[Double]]].length == 0){
        val arr = new Array[Double](128)
        List(arr.toList)
      } else {
        map("value").asInstanceOf[List[List[Double]]]
      }
    }
    else null
  }

  private def _getBlockingStub(): FaceFeatureApiGrpc.FaceFeatureApiBlockingStub = {
    FaceFeatureApiGrpc.newBlockingStub(_channel)
  }
}
