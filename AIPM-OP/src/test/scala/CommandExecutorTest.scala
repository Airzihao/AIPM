import org.junit.{Assert, Test}

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 10:14 2020/10/7
 * @Modified By:
 */
class CommandExecutorTest extends CommandTest {
//  val cmd1: String = "cmd echo HELLO AIPM!!!"

  @Test
  def test1(): Unit = {
    val commandExecutor = new CommandExecutor
//    val cmd = new BashExecCommand("python", "./test/resources/test.py").getAsString()
    commandExecutor.execute("python")
    commandExecutor.execute("import ./src/test/resources/test.py as test")
    val result = commandExecutor.execute("test.helloFunc()")
    Assert.assertEquals("HELLO AIPM!!!", result)
  }

}
