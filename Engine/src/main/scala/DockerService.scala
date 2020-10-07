import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:56 2020/9/30
 * @Modified By:
 */
class DockerService {

  val dockerClient: DockerClient = DockerClientBuilder.getInstance("").build()
  val info = dockerClient.listContainersCmd().exec()
  val str: String = ""
}
