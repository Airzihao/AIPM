import org.junit.{Assert, Test}

/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 23:32 2020/10/6
  * @Modified By:
  */

class CommandGeneratorTest extends CommandTest {

  val args1 = Array("arg0", "arg1")
  val args2 = Array("arg0")
  val args3 = Array("")

  @Test
  def testGetAsString(): Unit ={
    val cmd1 = new BashExecCommand(envStr, pyFileStr, args1)
    Assert.assertEquals("python3 ./test.py arg0 arg1", cmd1.getAsString())

    val cmd2 = new BashExecCommand(envStr, pyFileStr, args2)
    Assert.assertEquals("python3 ./test.py arg0", cmd2.getAsString())

    val cmd3 = new BashExecCommand(envStr, pyFileStr, args3)
    Assert.assertEquals("python3 ./test.py", cmd3.getAsString())
  }

}
