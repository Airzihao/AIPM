import java.io.{File, FileWriter}

import com.google.protobuf.ByteString
import org.grapheco.aipm.common.utils.AipmFileOp
import org.grapheco.aipm.rpc.FaceFeatureClient
import org.junit.runners.MethodSorters
import org.junit.{Assert, FixMethodOrder, Test}
import org.neo4j.blob.Blob
import org.neo4j.blob.impl.BlobFactory

import scala.collection.immutable.Map
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.Success
import scala.util.parsing.json.JSON

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 20:16 2020/10/7
 * @Modified By:
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FaceFeatureClientTest {
  val rpcServerIp: String = "127.0.0.1:8081"
  val client = new FaceFeatureClient(rpcServerIp)
  val imgFilePath1: String = "D:/PySpace/AIPM-OPCollection/data/face/temp1.jpg"
  val imgFilePath2: String = "D:/PySpace/AIPM-OPCollection/data/face/temp2.jpg"
  val imgFilePath3: String = "D:/PySpace/AIPM-OPCollection/data/face/temp3.jpg"

  @Test
  def urlRequest(): Unit = {
    // get features from url
    val result: List[List[Double]] = client.getFaceFeatures(imgFilePath1)
    val expectedResultStr = Source.fromFile("./src/test/resources/result1.txt", "utf-8").mkString
    val expectedResult: List[List[Double]] = _wrapResult(expectedResultStr)
    Assert.assertEquals(expectedResult, result)
  }

  @Test
  def bytesRequest(): Unit = {
    // get features from bytes
    val imgBytes: ByteString = ByteString.copyFrom(AipmFileOp.getBinFileAsBytesArr(new File(imgFilePath2)))
    val result: List[List[Double]] = client.getFaceFeatures(imgBytes)
    val expectedResultStr = Source.fromFile("./src/test/resources/result2.txt", "utf-8").mkString
    val expectedResult2 = _wrapResult(expectedResultStr)
    Assert.assertEquals(expectedResult2, result)
  }


  @Test
  def blobRequest(): Unit = {
    val blob: Blob = BlobFactory.fromFile(new File(imgFilePath3)) //300ms
    val result: List[List[Double]] = client.getFaceFeatures(blob)
    val expectedResultStr = Source.fromFile("./src/test/resources/result3.txt", "utf-8").mkString
    val expectedResult3 = _wrapResult(expectedResultStr)
    Assert.assertEquals(expectedResult3, result)
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

  def serialServer(repeatTime: Int, fileWriter: FileWriter): Unit = {
    val serialServerClient = new FaceFeatureClient("127.0.0.1:8081")
    val time0 = System.currentTimeMillis()
    syncClientTest(serialServerClient, repeatTime)
    val time1 = System.currentTimeMillis()
    asyncClientTest(serialServerClient, repeatTime)
    val time2 = System.currentTimeMillis()
    println(s"On serial server, syncClient $repeatTime costs ${time1-time0}")
    println(s"On serial server, asyncClient $repeatTime costs ${time2-time1}")
    fileWriter.write(s"On serial server, syncClient $repeatTime costs ${time1-time0}\n")
    fileWriter.write(s"On serial server, asyncClient $repeatTime costs ${time2-time1}\n")
    fileWriter.flush()
  }


  def parallelServer(repeatTime: Int, fileWriter: FileWriter): Unit = {
    val parallelServerClient = new FaceFeatureClient("127.0.0.1:8082")
    val time0 = System.currentTimeMillis()
    syncClientTest(parallelServerClient, repeatTime)
    val time1 = System.currentTimeMillis()
    asyncClientTest(parallelServerClient, repeatTime)
    val time2 = System.currentTimeMillis()
    println(s"On parallel server, syncClient $repeatTime cost ${time1-time0}")
    println(s"On parallel server, asyncClient $repeatTime cost ${time2-time1}")
    fileWriter.write(s"On parallel server, syncClient $repeatTime cost ${time1-time0}\n")
    fileWriter.write(s"On parallel server, asyncClient $repeatTime cost ${time2-time1}\n")
    fileWriter.flush()
  }

  @Test
  def test(): Unit ={
    val file: File = new File("./record2cores.txt")
    val fileWriter = new FileWriter(file)
    val repeatTimeList = List(1, 5, 10, 20, 50, 100, 200, 500)
    repeatTimeList.foreach(t => {
      serialServer(t,fileWriter)
      parallelServer(t,fileWriter)
    })
    fileWriter.flush()
    fileWriter.close()
  }



  def syncClientTest(_client: FaceFeatureClient, repeatTime: Int): Unit = {
    val blob: Blob = BlobFactory.fromFile(new File(imgFilePath1))
    for (i<-1 to repeatTime) {
      _client.getFaceFeatures(blob)
    }
  }

  def asyncClientTest(_client: FaceFeatureClient, repeatTime: Int): Unit ={
    val blob: Blob = BlobFactory.fromFile(new File(imgFilePath1))
    val futureResultBuf: ArrayBuffer[Future[List[List[Double]]]] = ArrayBuffer[Future[List[List[Double]]]]()
    for (i<-1 to repeatTime) {
      futureResultBuf.append(_client.asyncGetFaceFeatures(blob))
    }
    val futureResultList = futureResultBuf.toArray
    futureResultList.foreach(f => f.onComplete {
      case Success(result) => "do nothing"
    })
    futureResultList.foreach(f => Await.result(f, Duration.Inf))
  }
}
