import org.junit.{Assert, Test}

/**
  * @Author: Airzihao
  * @Description:
  * @Date: Created in 23:32 2020/10/6
  * @Modified By:
  */
class CommandGeneratorTest {
  val envStr: String = "python3"
  val bashFileStr = "./test.py"
  val args1 = Array("arg0", "arg1")
  val args2 = Array("arg0")
  val args3 = Array("")

  @Test
  def testGetAsString(): Unit ={
    val cmd1 = new PyBashExecCommand(envStr, bashFileStr, args1)
    Assert.assertEquals("python3 ./test.py arg0 arg1", cmd1.getAsString())

    val cmd2 = new PyBashExecCommand(envStr, bashFileStr, args2)
    Assert.assertEquals("python3 ./test.py arg0", cmd2.getAsString())

    val cmd3 = new PyBashExecCommand(envStr, bashFileStr, args3)
    Assert.assertEquals("python3 ./test.py", cmd3.getAsString())


  }


}
