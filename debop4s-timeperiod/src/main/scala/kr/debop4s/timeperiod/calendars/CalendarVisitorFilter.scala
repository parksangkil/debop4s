package kr.debop4s.timeperiod.calendars

import kr.debop4s.timeperiod.DayOfWeek.DayOfWeek
import kr.debop4s.timeperiod._
import kr.debop4s.timeperiod.{TimePeriodCollection, ITimePeriodCollection}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * kr.debop4s.timeperiod.calendars.CalendarVisitorFilter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 1. 3. 오전 10:17
 */
@SerialVersionUID(3032428178497692848L)
class CalendarVisitorFilter extends ICalendarVisitorFilter {

  val _excludePeriods = new TimePeriodCollection()
  val _years = ArrayBuffer[Int]()
  val _monthOfYears = ArrayBuffer[Int]()
  val _dayOfMonths = ArrayBuffer[Int]()
  val _hourOfDays = ArrayBuffer[Int]()
  val _minuteOfHours = ArrayBuffer[Int]()
  val _weekDays = mutable.HashSet[DayOfWeek]()

  def excludePeriods: ITimePeriodCollection = _excludePeriods

  def years: Seq[Int] = _years

  def monthOfYears: Seq[Int] = _monthOfYears

  def dayOfMonths: Seq[Int] = _dayOfMonths

  def weekDays: mutable.Set[DayOfWeek] = _weekDays

  def hourOfDays: Seq[Int] = _hourOfDays

  def minuteOfHours: Seq[Int] = _minuteOfHours

  def addWorkingWeekdays() {
    addWeekdays(Weekdays: _*)
  }

  def addWorkingWeekends() {
    addWeekdays(Weekends: _*)
  }

  def addWeekdays(days: DayOfWeek*) {
    _weekDays ++= days
  }

  def clear() {
    _years.clear()
    _monthOfYears.clear()
    _dayOfMonths.clear()
    _hourOfDays.clear()
    _minuteOfHours.clear()
    _weekDays.clear()
  }

  override def toString: String = Calendars.asString(this)
}
