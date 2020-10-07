import java.io.{BufferedReader, InputStream, InputStreamReader}

import org.grapheco.aipm.common.utils.CmdExecError

/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 22:55 2020/10/6
  * @Modified By:
  */

class CommandExecutor extends Executor {
  // to avoid frequently get runtime msg
  val runtime = Runtime.getRuntime()

  def execute(cmd: String): String = {
    val process = runtime.exec(cmd)
//    process.waitFor()
    _getResultFromProcess(process)
  }

  private def _getResultFromProcess(process: Process): String = {
    val is: InputStream = process.getInputStream()
    val br: BufferedReader = new BufferedReader(new InputStreamReader(is))
    val stringBuffer = new StringBuffer()
    var line: String = br.readLine()
    while (line != null) {
      stringBuffer.append(line)
      line = br.readLine()
    }
//
    stringBuffer.toString
  }

}

trait Executor {
  val runtime: Runtime
  def execute(cmd: String): Any
}
