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
    val info: Info = dockerClient.infoCmd().exec()

  }

}
