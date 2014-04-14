package debop4s.timeperiod.timerange

import debop4s.core.jodatime._
import debop4s.timeperiod._
import debop4s.timeperiod.utils.Times
import org.joda.time.DateTime

/**
 * debop4s.timeperiod.timerange.MinuteRangeCollection
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 28. 오후 9:00
 */
@SerialVersionUID(-5566298718095890768L)
class MinuteRangeCollection(private[this] val _moment: DateTime,
                            private[this] val _minuteCount: Int,
                            private[this] val _calendar: ITimeCalendar = DefaultTimeCalendar)
    extends MinuteTimeRange(_moment, _minuteCount, _calendar) {


    @inline
    def minutes = {
        val startMin = Times.trimToSecond(start)
        (0 until minuteCount).view.map {
            m =>
                MinuteRange(startMin + m.minute, calendar)
        }
    }
}

object MinuteRangeCollection {

    def apply(moment: DateTime, minuteCount: Int): MinuteRangeCollection = {
        apply(moment, minuteCount, DefaultTimeCalendar)
    }

    def apply(moment: DateTime, minuteCount: Int, calendar: ITimeCalendar): MinuteRangeCollection = {
        new MinuteRangeCollection(moment, minuteCount, calendar)
    }

    def apply(year: Int,
              monthOfYear: Int,
              dayOfMonth: Int,
              hourOfDay: Int,
              minuteOfHour: Int,
              minuteCount: Int): MinuteRangeCollection = {
        apply(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, minuteCount, DefaultTimeCalendar)
    }

    def apply(year: Int,
              monthOfYear: Int,
              dayOfMonth: Int,
              hourOfDay: Int,
              minuteOfHour: Int,
              minuteCount: Int,
              calendar: ITimeCalendar): MinuteRangeCollection = {
        new MinuteRangeCollection(new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour),
            minuteCount,
            calendar)
    }


}
