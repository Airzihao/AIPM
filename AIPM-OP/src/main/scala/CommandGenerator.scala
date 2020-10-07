/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 23:05 2020/10/6
  * @Modified By:
  */
object CommandGenerator {


}

// todo: test basicly run cmd line!
case class BashExecCommand(env: String, bashFile: String, val args: Array[String] =  Array("")) extends Command {
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