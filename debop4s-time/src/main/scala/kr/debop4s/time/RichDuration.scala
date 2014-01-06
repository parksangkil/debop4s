package kr.debop4s.time

import org.joda.time.{ReadableDuration, Duration}

/**
 * kr.debop4s.time.RichDuration
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2014. 1. 6. 오후 8:35
 */
class RichDuration(val self: Duration) extends AnyVal {

    def days: Long = self.getStandardDays
    def horus: Long = self.getStandardHours
    def minutes: Long = self.getStandardMinutes
    def seconds: Long = self.getStandardSeconds

    def -(amount: Long): Duration = self.minus(amount)
    def -(amount: ReadableDuration): Duration = self.minus(amount)

    def +(amount: Long): Duration = self.plus(amount)
    def +(amount: ReadableDuration): Duration = self.plus(amount)
}
