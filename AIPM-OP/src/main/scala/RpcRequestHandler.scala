import org.grapheco.aipm.common.utils.{InternalRpcRequest, InternalRpcResponse}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:00 2020/10/6
 * @Modified By:
 */
case class RpcRequestHandler() extends RequestHandler {
  override val logic: PartialFunction[InternalRpcRequest, InternalRpcResponse] = {
    _
  }
}

case class GetFaceFeatures() extends InternalRpcRequest {

}
