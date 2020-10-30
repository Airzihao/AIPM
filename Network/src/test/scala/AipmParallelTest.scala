import java.io.{File, FileWriter}

import org.grapheco.aipm.rpc.FaceFeatureClient
import org.junit.Test
import org.neo4j.blob.Blob
import org.neo4j.blob.impl.BlobFactory

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:43 2020/10/30
 * @Modified By:
 */
class AipmParallelTest {
  val imgFilePath1: String = "D:/PySpace/AIPM-OPCollection/data/face/temp1.jpg"
  val blob: Blob = BlobFactory.fromFile(new File(imgFilePath1))

  @Test
  def test(): Unit ={
    val file: File = new File("./record1.txt")
    val fileWriter = new FileWriter(file)
    val repeatTimeList = List(1, 5, 10, 20, 50, 100, 200, 500)
    repeatTimeList.foreach(t => {
      serialServer(t,fileWriter)
      parallelServer(t,fileWriter)
    })
    fileWriter.flush()
    fileWriter.close()
  }

  def serialServer(repeatTime: Int, fileWriter: FileWriter): Unit = {
    val serialServerClient = new FaceFeatureClient("10.0.82.220:9091")
    val time0 = System.currentTimeMillis()
    syncClientTest(serialServerClient, repeatTime, blob)
    val time1 = System.currentTimeMillis()
    asyncClientTest(serialServerClient, repeatTime, blob)
    val time2 = System.currentTimeMillis()
    println(s"On serial server, syncClient $repeatTime costs ${time1-time0}")
    println(s"On serial server, asyncClient $repeatTime costs ${time2-time1}")
    fileWriter.write(s"On serial server, syncClient $repeatTime costs ${time1-time0}\n")
    fileWriter.write(s"On serial server, asyncClient $repeatTime costs ${time2-time1}\n")
    fileWriter.flush()
  }

  def parallelServer(repeatTime: Int, fileWriter: FileWriter): Unit = {
    val parallelServerClient = new FaceFeatureClient("10.0.82.220:9094")
    val time0 = System.currentTimeMillis()
    syncClientTest(parallelServerClient, repeatTime, blob)
    val time1 = System.currentTimeMillis()
    asyncClientTest(parallelServerClient, repeatTime, blob)
    val time2 = System.currentTimeMillis()
    println(s"On parallel server, syncClient $repeatTime cost ${time1-time0}")
    println(s"On parallel server, asyncClient $repeatTime cost ${time2-time1}")
    fileWriter.write(s"On parallel server, syncClient $repeatTime cost ${time1-time0}\n")
    fileWriter.write(s"On parallel server, asyncClient $repeatTime cost ${time2-time1}\n")
    fileWriter.flush()
  }

  def syncClientTest(_client: FaceFeatureClient, repeatTime: Int, blob: Blob): Unit = {
    for (i<-1 to repeatTime) {
      _client.getFaceFeatures(blob)
    }
  }
  def asyncClientTest(_client: FaceFeatureClient, repeatTime: Int, blob: Blob): Unit ={
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
