package kr.debop4s.timeperiod.timerange

import kr.debop4s.timeperiod._
import kr.debop4s.timeperiod.utils.Times
import org.joda.time.DateTime

/**
 * kr.debop4s.timeperiod.timerange.WeekRange
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 28. 오후 11:18
 */
class WeekRange(private val _year: Int,
                val weekOfYear: Int,
                private val _calendar: ITimeCalendar = DefaultTimeCalendar)
  extends WeekTimeRange(Times.startTimeOfWeek(_year, weekOfYear, _calendar), 1, _calendar) {

  def firstDayOfWeek: DateTime = getStart

  def lastDayOfWeek: DateTime = firstDayOfWeek.plusDays(6)

  def isMultipleCalendarYears: Boolean =
    calendar.getYear(firstDayOfWeek) != calendar.getYear(lastDayOfWeek)

  def previousWeek: WeekRange = addWeeks(-1)

  def nextWeek: WeekRange = addWeeks(1)

  def addWeeks(weeks: Int): WeekRange = {
    WeekRange(Times.getStartOfYearWeek(year, weekOfYear, calendar).plusWeeks(weeks), calendar)
  }
}

object WeekRange {

  def apply(year: Int, weekOfYear: Int): WeekRange =
    apply(year, weekOfYear, DefaultTimeCalendar)

  def apply(year: Int, weekOfYear: Int, calendar: ITimeCalendar): WeekRange =
    new WeekRange(year, weekOfYear, calendar)

  def apply(moment: DateTime): WeekRange = apply(moment, DefaultTimeCalendar)

  def apply(moment: DateTime, calendar: ITimeCalendar): WeekRange =
    new WeekRange(moment.getWeekyear, moment.getWeekOfWeekyear, calendar)

}
