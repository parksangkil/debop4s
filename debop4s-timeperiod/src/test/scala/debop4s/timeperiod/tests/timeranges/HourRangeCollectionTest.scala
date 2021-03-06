package debop4s.timeperiod.tests.timeranges

import debop4s.core.parallels.Parallels
import debop4s.timeperiod._
import debop4s.timeperiod.tests.AbstractTimePeriodTest
import debop4s.timeperiod.timerange.{HourRange, HourRangeCollection}
import debop4s.timeperiod.utils.Times
import org.joda.time.DateTime

/**
 * HourRangeCollectionTest
 * Created by debop on 2014. 2. 16.
 */
class HourRangeCollectionTest extends AbstractTimePeriodTest {

  test("single hour") {
    val startTime = new DateTime(2004, 2, 22, 17, 0)
    val hours = HourRangeCollection(startTime, 1, EmptyOffsetTimeCalendar)

    hours.hourCount should equal(1)
    hours.startYear should equal(startTime.getYear)
    hours.startMonthOfYear should equal(startTime.getMonthOfYear)
    hours.startDayOfMonth should equal(startTime.getDayOfMonth)
    hours.startHourOfDay should equal(startTime.getHourOfDay)

    hours.endYear should equal(startTime.getYear)
    hours.endMonthOfYear should equal(startTime.getMonthOfYear)
    hours.endDayOfMonth should equal(startTime.getDayOfMonth)
    hours.endHourOfDay should equal(startTime.getHourOfDay + 1)

    val hourList = hours.hours
    hourList.size should equal(1)
    hourList.head.isSamePeriod(HourRange(startTime, EmptyOffsetTimeCalendar)) should equal(true)
  }

  test("calendar hours") {
    val startTime = new DateTime(2004, 2, 11, 22, 0)
    val hourCount = 14
    val hours = HourRangeCollection(startTime, hourCount, EmptyOffsetTimeCalendar)

    hours.hourCount should equal(hourCount)
    hours.startYear should equal(startTime.getYear)
    hours.startMonthOfYear should equal(startTime.getMonthOfYear)
    hours.startDayOfMonth should equal(startTime.getDayOfMonth)
    hours.startHourOfDay should equal(startTime.getHourOfDay)

    hours.endYear should equal(startTime.getYear)
    hours.endMonthOfYear should equal(startTime.getMonthOfYear)
    hours.endDayOfMonth should equal(startTime.getDayOfMonth + 1)
    hours.endHourOfDay should equal((startTime.getHourOfDay + hourCount) % 24)

    val hourList = hours.hours
    hourList.size should equal(hourCount)
    for (h <- 0 until hourCount) {
      hourList(h).isSamePeriod(HourRange(startTime.plusHours(h), EmptyOffsetTimeCalendar)) should equal(true)
    }
  }

  test("hours test") {
    val hourCounts = Array(1, 24, 48, 64, 128)
    val now = Times.now

    hourCounts.foreach {
      hourCount =>
        val hourRanges = HourRangeCollection(now, hourCount)
        val startTime = Times.trimToMinute(now) + hourRanges.calendar.startOffset
        val endTime = startTime.plusHours(hourCount) + hourRanges.calendar.endOffset

        val items = hourRanges.hours
        items.size should equal(hourCount)

        Parallels.runAction1(hourCount) {
          h =>
            items(h).start should equal(startTime.plusHours(h))
            items(h).end should equal(hourRanges.calendar.mapEnd(startTime.plusHours(h + 1)))
            items(h).unmappedEnd should equal(startTime.plusHours(h + 1))
        }
    }
  }

}
