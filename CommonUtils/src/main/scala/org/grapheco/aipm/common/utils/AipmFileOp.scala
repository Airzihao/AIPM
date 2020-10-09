package org.grapheco.aipm.common.utils

import java.io.{File, FileInputStream}

import org.neo4j.blob.Blob

/**
 * @Author: Airzihao
 * @Description:
 * @Date: Created at 12:46 2020/10/9
 * @Modified By:
 */
object AipmFileOp {
  def getBinFileAsBytesArr(file: File): Array[Byte] = {
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    bytes
  }

  def getBlobAsBytesArr(blob: Blob): Array[Byte] = {
    blob.toBytes()
  }

}
