package debop4s.core

/**
 * Byte 배열 정보를 문자열로 표현할 방식
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 9. 오후 10:20
 */
object BinaryStringFormat extends Enumeration {

  type BinaryStringFormat = Value

  /**
   * Base64 인코딩
   */
  val Base64 = Value(0, "Base64")

  /**
   * Hex Decimal
   */
  val HexDecimal = Value(1, "HexDecimal")

}
