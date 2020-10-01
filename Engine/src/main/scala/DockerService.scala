import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Info
import com.github.dockerjava.core.DockerClientBuilder
import com.google.gson.Gson

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 16:56 2020/9/30
 * @Modified By:
 */
class DockerService {

  val dockerClient: DockerClient = DockerClientBuilder.getInstance("tcp://10.0.90.173:2375").build()
  val info: Info = dockerClient.infoCmd().exec()
  val str: String = ""
}
