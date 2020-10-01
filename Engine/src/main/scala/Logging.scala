import org.slf4j.LoggerFactory

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 18:44 2020/9/30
 * @Modified By:
 */
trait Logging {
  val logger = LoggerFactory.getLogger(this.getClass);
}
