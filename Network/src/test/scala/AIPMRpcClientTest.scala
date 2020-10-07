import org.grapheco.aipm.rpc.AIPMRpcClient
import org.junit.Test
import com.google.gson.{JsonObject, JsonParser}
import scala.io.Source

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:16 2020/10/7
 * @Modified By:
 */
class AIPMRpcClientTest {
  val rpcServerIp: String = "159.226.193.204:8081"
  val client = new AIPMRpcClient(rpcServerIp)

  @Test
  def test1(): Unit = {
    val json = new JsonParser
//    val result1 = json.parse(client.getFaceFeature("D:/PySpace/AIPM-OPCollection/data/face/temp.jpg").replace("\n", "")).asInstanceOf[JsonObject]
//    val result2 = json.parse(client.getFaceFeature("D:/PySpace/AIPM-OPCollection/data/face/temp2.jpg").replace("\n", "")).asInstanceOf[JsonObject]
//    val expectedResult1 = json.parse(Source.fromFile("./src/test/resources/result.txt", "utf-8").bufferedReader())
//    val expectedResult2 = json.parse(Source.fromFile("./src/test/resources/result2.txt", "utf-8").bufferedReader())
//    print(expectedResult1.equals(expectedResult1))
  }

}
