package kr.debop4s.timeperiod

import kr.debop4s.core.ValueObject
import kr.debop4s.core.utils.Hashs

/**
 * kr.debop4s.timeperiod.DayRangeInMonth
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2014. 1. 2. 오후 8:48
 */
@SerialVersionUID(1287074885972003104L)
class DayRangeInMonth(val startDayOfMonth: Int, val endDayOfMonth: Int) extends ValueObject with Ordering[DayRangeInMonth] {

  def isSingleDay: Boolean = startDayOfMonth == endDayOfMonth

  def hasInside(dayOfMonth: Int) =
    startDayOfMonth <= dayOfMonth && dayOfMonth <= endDayOfMonth

  def assertValidDayRange(dayOfMonth: Int) {
    assert(dayOfMonth > 0 && dayOfMonth <= MaxDaysPerMonth,
            s"dayOfMonth=[$dayOfMonth] 는 1~$MaxDaysPerMonth 사이여야 합니다.")
  }

  def compare(x: DayRangeInMonth, y: DayRangeInMonth) =
    x.startDayOfMonth.compareTo(endDayOfMonth)

  override def hashCode() = Hashs.compute(startDayOfMonth, endDayOfMonth)

  override protected def buildStringHelper =
    super.buildStringHelper
    .add("startDayOfMonth", startDayOfMonth)
    .add("endDayOfMonth", endDayOfMonth)
}
