package debop4s.timeperiod.calendars

import debop4s.timeperiod.{MonthRangeInYear, DayRangeInMonth}

/**
 * debop4s.timeperiod.calendars.ICalendarPeriodCollectionFilter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 1. 3. 오전 10:40
 */
trait ICalendarPeriodCollectorFilter extends ICalendarVisitorFilter {

  def collectingMonths: Seq[MonthRangeInYear]

  def collectingDays: Seq[DayRangeInMonth]

}
