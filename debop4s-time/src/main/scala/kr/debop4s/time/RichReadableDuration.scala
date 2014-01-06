package kr.debop4s.time

import org.joda.time.ReadableDuration

/**
 * kr.debop4s.time.RichReadableDuration
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2014. 1. 6. 오후 9:00
 */
class RichReadableDuration(val self: ReadableDuration) extends AnyVal with Ordered[ReadableDuration] {

    def millis: Long = self.getMillis

    def compare(that: ReadableDuration): Int = self.compareTo(that)
}
