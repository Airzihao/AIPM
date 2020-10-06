/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 23:05 2020/10/6
  * @Modified By:
  */
object CommandGenerator {


}

case class PyBashExecCommand(env: String, bashFile: String, args: Array[String]) extends Command {
  // the expected evn value is python or python3
  override def getAsString(): String = {
    val argsAsString: String = {
      var argsString = ""
      if(args.length > 0) args.map(item => argsString = argsString + item + " ")
      argsString.trim
    }

    val cmd = s"$env $bashFile $argsAsString"
    cmd.trim
  }
}

trait Command {
  def getAsString(): String
}