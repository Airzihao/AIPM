import java.util

import com.google.gson.reflect.TypeToken
import org.grapheco.aipm.rpc.{AIPMRpcClient, FaceFeatureClient}
import org.junit.{Assert, Test}
import com.google.gson.{Gson, JsonArray, JsonObject, JsonParser}
import org.grapheco.aipm.common.utils.WrongArgsException

import scala.collection.JavaConversions.{asScalaBuffer, collectionAsScalaIterable, iterableAsScalaIterable}
import scala.io.Source
import scala.collection.JavaConverters._

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:16 2020/10/7
 * @Modified By:
 */
class FaceFeatureClientTest {
  val rpcServerIp: String = "127.0.0.1:8081"
  val client = new AIPMRpcClient(rpcServerIp)
  val client2 = new FaceFeatureClient(rpcServerIp)

  @Test
  def test1(): Unit = {
    // get features from url

    val result: List[List[Double]] = client.getFaceFeatures("D:/PySpace/AIPM-OPCollection/data/face/temp1.jpg")
    val json = new JsonParser
    val expectedResultStr = json.parse(Source.fromFile("./src/test/resources/result1.txt", "utf-8").bufferedReader()).getAsString
    val expectedResult: List[List[Double]] = _wrapResult[List[List[Double]]](expectedResultStr, "value")
//    Assert.assertEquals(true, isJsonObjectEquals(result1, expectedResult1))
//    Assert.assertEquals(true, isJsonObjectEquals(result2, expectedResult2))
    val jsonStr = Source.fromFile("./src/test/resources/result1.txt", "utf-8").mkString
//    val jsonStr = expectedResult1.getAsString
    val jsonArray = new JsonParser().parse(jsonStr).getAsJsonObject.getAsJsonArray("value").size()

    Assert.assertEquals(expectedResult, result)
  }

  @Test
  def test2(): Unit = {
    // get features from bytes
    val json = new JsonParser
    val expectedResult2 = json.parse(Source.fromFile("./src/test/resources/result2.txt", "utf-8").bufferedReader()).getAsJsonObject
  }



  private def isJsonObjectEquals(jsonObj1: JsonObject, jsonObj2: JsonObject): Boolean = {
    var flag = true
    if (jsonObj1.keySet().toArray().length != jsonObj2.keySet().toArray().length) return false
    jsonObj1.keySet().toArray.foreach(key => {
      if(jsonObj1.get(key.toString) != jsonObj2.get(key.toString)) {
        flag = false
      }
    })
    flag
  }

  private def _wrapResult[T](jsonStr: String, fieldName: String): T = {
    val jsonObj: JsonObject = new JsonParser().parse(jsonStr).asInstanceOf[JsonObject]
    if (!jsonObj.has(fieldName)) {
      throw new WrongArgsException(s"No $fieldName in the result.")
    }
    val _jsonElement = jsonObj.get(fieldName)
    val result: T = _jsonElement match {
      case _j: JsonArray => new Gson().fromJson(_jsonElement, new TypeToken[T](){}.getType)
    }
    result.asInstanceOf[T]
  }


}
