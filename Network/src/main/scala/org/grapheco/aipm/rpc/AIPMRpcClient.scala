package org.grapheco.aipm.rpc
import io.grpc.ManagedChannel
import org.grapheco.aipm.common.utils.{AIPMRpcError, GlobalContext, Logging}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 19:14 2020/10/7
 * @Modified By:
 */
class AIPMRpcClient(val rpcServerIp: String = GlobalContext.getAIPMRpcServerIp()) extends Logging{


  val channel = _getChannel()
  private def _getChannel(): ManagedChannel = {
    // fixme: why it is red????
    io.grpc.ManagedChannelBuilder.forTarget(rpcServerIp).usePlaintext().build();
  }
  private def _getBlockingStub(): FaceFeatureApiGrpc.FaceFeatureApiBlockingStub = {
    FaceFeatureApiGrpc.newBlockingStub(channel)
  }

  def getFaceFeature(filePath: String): String = {
    try {
      val request: FaceFeatureService.UrlFaceFeatureRequest = FaceFeatureService.UrlFaceFeatureRequest.newBuilder().setImgUrl(filePath).build()
      val response: FaceFeatureService.FaceFeatureResponse = _getBlockingStub().getFaceFeature(request)
      response.getJsonResultStr
    } catch {
      case e: Exception => {
        logger.error(e.getMessage)
        throw new AIPMRpcError(e.getMessage)
      }
    }
  }


}
