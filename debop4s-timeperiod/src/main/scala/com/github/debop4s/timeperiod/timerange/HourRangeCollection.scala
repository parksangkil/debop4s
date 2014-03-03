package com.github.debop4s.timeperiod.timerange

import com.github.debop4s.timeperiod._
import com.github.debop4s.timeperiod.utils.Times
import org.joda.time.DateTime
import scala.collection.mutable.ArrayBuffer

/**
 * com.github.debop4s.timeperiod.timerange.HourCollectionRange
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 28. 오후 10:06
 */
@SerialVersionUID(8973240176036662074L)
class HourRangeCollection(private[this] val _moment: DateTime,
                          private[this] val _hourCount: Int,
                          private[this] val _calendar: ITimeCalendar = DefaultTimeCalendar)
  extends HourTimeRange(Times.trimToSecond(_moment), _hourCount, _calendar) {

  def getHours: Seq[HourRange] = {
    val startHour = Times.trimToMinute(start)

    val hours = ArrayBuffer[HourRange]()
    for (h <- 0 until hourCount) {
      hours += new HourRange(startHour.plusHours(h), calendar)
    }
    hours
  }
}

object HourRangeCollection {

  def apply(moment: DateTime, hourCount: Int): HourRangeCollection = {
    new HourRangeCollection(moment, hourCount, DefaultTimeCalendar)
  }

  def apply(moment: DateTime, hourCount: Int, calendar: ITimeCalendar): HourRangeCollection = {
    new HourRangeCollection(moment, hourCount, calendar)
  }

  def apply(year: Int,
            monthOfYear: Int,
            dayOfMonth: Int,
            hourOfDay: Int,
            hourCount: Int): HourRangeCollection = {
    apply(year, monthOfYear, dayOfMonth, hourOfDay, hourCount, DefaultTimeCalendar)
  }

  def apply(year: Int,
            monthOfYear: Int,
            dayOfMonth: Int,
            hourOfDay: Int,
            hourCount: Int,
            calendar: ITimeCalendar): HourRangeCollection = {
    new HourRangeCollection(new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, 0), hourCount, calendar)
  }


}
