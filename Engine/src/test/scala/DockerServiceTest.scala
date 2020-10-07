import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Info
import com.github.dockerjava.core.DockerClientBuilder
import org.junit.Test

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:13 2020/9/30
 * @Modified By:
 */
class DockerServiceTest {


  @Test
  def test1(): Unit ={
    val dockerClient: DockerClient = DockerClientBuilder.getInstance("tcp://10.0.90.173:2375").build()
    val info = dockerClient.listContainersCmd().exec()
//    val info1 = dockerClient.pullImageCmd().exec()
    val info2 = dockerClient.listImagesCmd().exec()
    val info3:Info = dockerClient.infoCmd().exec()
/*
     1. check the system info
     2. check whether the container running
     3. list the images, check whether the required image exists
     4. pull the images/run the container
     5. exec cmd
 */
//
//
    val s = ""

  }

}
