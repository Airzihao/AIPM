import java.io.FileWriter

import org.grapheco.aipm.rpc.FaceFeatureClient
import org.neo4j.blob.Blob

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 11:42 2020/10/30
 * @Modified By:
 */
class AipmTestBase {

  def parallelServer(ip: String, repeatTime: Int, fileWriter: FileWriter, blob: Blob): Unit = {
    val parallelServerClient = new FaceFeatureClient(ip)
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
