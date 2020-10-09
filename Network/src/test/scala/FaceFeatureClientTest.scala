

import java.io.File

import com.google.gson.reflect.TypeToken
import org.grapheco.aipm.rpc.{AipmRpcClient, FaceFeatureClient}
import org.junit.{Assert, Test}
import com.google.gson.{Gson, JsonArray, JsonObject, JsonParser}
import com.google.protobuf.{ByteString, CodedInputStream}
import org.grapheco.aipm.common.utils.AipmFileOp

import scala.collection.JavaConversions.{asScalaBuffer, collectionAsScalaIterable, iterableAsScalaIterable}
import scala.io.Source
import scala.collection.JavaConverters._
import scala.collection.immutable.Map
import scala.util.parsing.json.JSON

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:16 2020/10/7
 * @Modified By:
 */
class FaceFeatureClientTest {
  val rpcServerIp: String = "127.0.0.1:8081"
  val client = new FaceFeatureClient(rpcServerIp)
  val imgFilePath1:String = "D:/PySpace/AIPM-OPCollection/data/face/temp1.jpg"
  val imgFilePath2:String = "D:/PySpace/AIPM-OPCollection/data/face/temp2.jpg"

  @Test
  def test1(): Unit = {
    // get features from url
    val result: List[List[Double]] = client.getFaceFeatures(imgFilePath1)
    val expectedResultStr = Source.fromFile("./src/test/resources/result1.txt", "utf-8").mkString
    val expectedResult: List[List[Double]] = _wrapResult(expectedResultStr)
    Assert.assertEquals(expectedResult, result)
  }

  @Test
  def test2(): Unit = {
    // get features from bytes
    val imgBytes: ByteString = ByteString.copyFrom(AipmFileOp.getBinFileAsBytesArr(new File(imgFilePath2)))
    val result: List[List[Double]] = client.getFaceFeatures(imgBytes)
    val expectedResultStr = Source.fromFile("./src/test/resources/result2.txt", "utf-8").mkString
    val expectedResult2 = _wrapResult(expectedResultStr)
    Assert.assertEquals(expectedResult2, result)
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


}
