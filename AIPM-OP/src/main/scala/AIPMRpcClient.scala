import net.neoremind.kraps.RpcConf
import net.neoremind.kraps.rpc.netty.NettyRpcEnvFactory
import net.neoremind.kraps.rpc.{RpcAddress, RpcEnv, RpcEnvClientConfig}
import org.grapheco.aipm.common.utils.{Logging, NodeAddress, RpcConnectionError}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:45 2020/10/6
 * @Modified By:
 */
object AIPMRpcClient {

  // throw error if can't connect.
  def connect(serverAddress: NodeAddress): AIPMRpcClient = {
    try {
      new AIPMRpcClient(serverAddress)
    } catch {
      case e: Exception => throw new RpcConnectionError(s"Failed to connect to $serverAddress, check the server status.")
    }
  }
}

case class AIPMRpcClient(val serverAddress: NodeAddress) extends Logging {
  val rpcEnv: RpcEnv = {
    val rpcConf = new RpcConf()
    val config = RpcEnvClientConfig(rpcConf, "AIPMRpc-client")
    NettyRpcEnvFactory.create(config)
  }

  val endPointRef = rpcEnv.setupEndpointRef(RpcAddress(serverAddress.host, serverAddress.port), "AIPMRpc-service")

  def close(): Unit = {
    rpcEnv.stop(endPointRef)
  }

}
