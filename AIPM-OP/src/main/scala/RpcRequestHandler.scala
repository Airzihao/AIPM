import org.grapheco.aipm.common.utils.{InternalRpcRequest, InternalRpcResponse}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:00 2020/10/6
 * @Modified By:
 */

case class RpcRequestHandler() extends RequestHandler {
  override val logic: PartialFunction[InternalRpcRequest, InternalRpcResponse] = {
    case GetUrlFaceFeaturesRequest(url: String) =>
      GetFaceFeaturesResponse(result = "")
  }
}

case class GetUrlFaceFeaturesRequest(url: String) extends InternalRpcRequest {

}

// TODO: how to design the stream func?
//case class GetStreamFaceFeaturesRequest(stream: ) extends InternalRpcRequest {
//
//}

case class GetFaceFeaturesResponse(result: String) extends InternalRpcResponse {

}

