import org.grapheco.aipm.common.utils.CmdExecError

/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 22:55 2020/10/6
  * @Modified By:
  */
object CommandExecutor extends Executor {
  // to avoid frequently get runtime msg
  val runtime = Runtime.getRuntime()

  def execute(cmd: String): Any = {
    try{
      val result = runtime.exec(cmd)
      return result
    } catch {
      // how to offer the full err msg?
      case e: Exception => throw new CmdExecError(s"Failed to execute $cmd.")
    }
  }

}

trait Executor {

}
