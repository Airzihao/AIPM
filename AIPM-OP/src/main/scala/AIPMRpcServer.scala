import net.neoremind
import net.neoremind.kraps.RpcConf
import net.neoremind.kraps.rpc.{RpcCallContext, RpcEndpoint, RpcEnv, RpcEnvServerConfig}
import net.neoremind.kraps.rpc.netty.NettyRpcEnvFactory
import org.grapheco.aipm.common.utils.{InternalRpcRequest, InternalRpcResponse, Logging}

import scala.collection.mutable.ArrayBuffer
/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:18 2020/10/6
 * @Modified By:
 */
class AIPMRpcServer(host: String, port: Int, serverName: String) extends Logging{
  val config = RpcEnvServerConfig(new RpcConf(), serverName, host, port)
  val thisRpcEnv = NettyRpcEnvFactory.create(config)
  val handlers = ArrayBuffer[PartialFunction[InternalRpcRequest, InternalRpcResponse]]();

  val endpoint: RpcEndpoint = new RpcEndpoint {
    override val rpcEnv: RpcEnv = thisRpcEnv;

    override def receiveAndReply(context: RpcCallContext): PartialFunction[Any, Unit] = {
      case rpcRequest: InternalRpcRequest =>
        val rpcResponse = handlers.find {
          _.isDefinedAt(rpcRequest)
        }.map(_.apply(rpcRequest)).get
        context.reply(rpcResponse)
    }
  }

  def accept(handler: PartialFunction[InternalRpcRequest, InternalRpcResponse]): Unit = {
    handlers += handler;
  }
  def accept(handler: RequestHandler): Unit = {
    handlers += handler.logic
  }

  def start(onStarted: => Unit = {}): Unit = {
    thisRpcEnv.setupEndpoint(serverName, endpoint)
    onStarted;
    thisRpcEnv.awaitTermination()
  }

  def shutdown(): Unit = {
    thisRpcEnv.shutdown()
  }
}

trait RequestHandler {
  val logic: PartialFunction[InternalRpcRequest, InternalRpcResponse];
}
