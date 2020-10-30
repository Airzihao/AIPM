import java.io.{File, FileWriter}

import org.grapheco.aipm.rpc.FaceFeatureClient
import org.junit.Test
import org.neo4j.blob.Blob
import org.neo4j.blob.impl.BlobFactory

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:02 2020/10/30
 * @Modified By:
 */
class AipmMultiThreadsTest extends AipmTestBase {
  val imgFilePath1: String = "D:/PySpace/AIPM-OPCollection/data/face/temp1.jpg"
  val blob: Blob = BlobFactory.fromFile(new File(imgFilePath1))

  @Test
  def test(): Unit = {
    val client1Cores = new FaceFeatureClient("10.0.82.220:9091")
//    val client12Cores = new FaceFeatureClient("10.0.82.220:9012")
//    val client6Cores = new FaceFeatureClient("10.0.82.220:9096")
    val file: File = new File("./record6.txt")
    val fileWriter = new FileWriter(file)
    val repeatTimeList = List(1, 5, 10, 20, 50, 100, 200, 500, 1000)
    repeatTimeList.foreach(t => {
      execute(client1Cores,1, t, fileWriter)
//      execute(client12Cores, 12, t, fileWriter)
//      execute(client6Cores, 6, t , fileWriter)
    })
    fileWriter.flush()
    fileWriter.close()
  }


  def execute(client: FaceFeatureClient, cores: Int, repeatTime: Int, fileWriter: FileWriter): Unit = {
    val parallelServerClient = client
    val time0 = System.currentTimeMillis()
    asyncClientTest(parallelServerClient, repeatTime, blob)
    val time1 = System.currentTimeMillis()
    println(s"On $cores cores parallel server, asyncClient $repeatTime cost ${time1-time0}")
    fileWriter.write(s"On $cores cores parallel server, asyncClient $repeatTime cost ${time1-time0}\n")
    fileWriter.flush()
  }

}
