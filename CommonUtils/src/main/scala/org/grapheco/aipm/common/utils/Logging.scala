package org.grapheco.aipm.common.utils

import org.slf4j.LoggerFactory

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 17:19 2020/10/6
 * @Modified By:
 */
trait Logging {
  val logger = LoggerFactory.getLogger(this.getClass)
}
