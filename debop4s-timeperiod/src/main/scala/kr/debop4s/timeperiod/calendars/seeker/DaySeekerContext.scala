package kr.debop4s.timeperiod.calendars.seeker

import kr.debop4s.timeperiod.calendars.ICalendarVisitorContext
import kr.debop4s.timeperiod.timerange.DayRange

/**
 * kr.debop4s.timeperiod.calendars.seeker.DaySeekerContext
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2014. 1. 5. 오후 8:17
 */
class DaySeekerContext(val startDay: DayRange,
                       private val _dayCount: Int) extends ICalendarVisitorContext {

    val dayCount = Math.abs(_dayCount)

    var remainingDays = dayCount
    var foundDay: DayRange = null

    def isFinished: Boolean = remainingDays == 0

    def processDay(day: DayRange) {
        if (isFinished)
            return

        remainingDays -= 1

        if (isFinished)
            foundDay = day
    }

}
