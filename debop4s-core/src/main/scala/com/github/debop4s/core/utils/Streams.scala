package com.github.debop4s.core.utils

import java.io.{ByteArrayOutputStream, OutputStream, InputStream}
import java.nio.charset.Charset
import org.slf4j.LoggerFactory

/**
 * com.github.debop4s.core.tools.Streams
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 9. 오후 11:00
 */
object Streams {

  private lazy val log = LoggerFactory.getLogger(getClass)

  val BUFFER_SIZE = 4096

  def copy(inputStream: InputStream, outputStream: OutputStream): Long = {
    assert(inputStream != null)
    assert(outputStream != null)

    val buffer = new Array[Byte](BUFFER_SIZE)
    var size = 0L
    var n = 0
    while (true) {
      n = inputStream.read(buffer, 0, BUFFER_SIZE)
      if (n > 0) {
        outputStream.write(buffer, 0, n)
        size += n
      }
      else
        return size
    }
    size
  }

  def toByteArray(is: InputStream): Array[Byte] = {

    val os = new ByteArrayOutputStream()
    try {
      copy(is, os)
      os.toByteArray
    } finally {
      os.close()
    }
  }

  def toOutputStream(bytes: Array[Byte]): OutputStream = {
    if (bytes == null || bytes.length == 0)
      return new ByteArrayOutputStream()

    val os = new ByteArrayOutputStream(bytes.length)
    os.write(bytes)
    os
  }

  def toOutputStream(str: String, cs: Charset = Charsets.UTF_8): OutputStream = {
    if (Strings.isEmpty(str))
      new ByteArrayOutputStream()
    else
      toOutputStream(str.getBytes(cs))
  }

  def toString(is: InputStream): String = Strings.getUtf8String(toByteArray(is))

  def toString(is: InputStream, cs: Charset = Charsets.UTF_8): String =
    Strings.getUtf8String(toByteArray(is))


  // TODO: Scala 고유의 Stream 처리 기능을 제공하자.
}
