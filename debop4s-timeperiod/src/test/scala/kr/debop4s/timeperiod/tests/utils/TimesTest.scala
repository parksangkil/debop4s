package kr.debop4s.timeperiod.tests.utils

import kr.debop4s.timeperiod.DayOfWeek._
import kr.debop4s.timeperiod._
import kr.debop4s.timeperiod.tests.AbstractTimePeriodTest
import kr.debop4s.timeperiod.utils.Times._
import kr.debop4s.timeperiod.utils.{Durations, Times}
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTimeZone, Duration, DateTime}
import scala.actors.threadpool.AtomicInteger
import scala.collection.JavaConversions._

/**
 * kr.debop4s.timeperiod.tests.utils.TimesTest 
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 2. 17. 오후 1:42
 */

class TimesCalendarTest extends AbstractTimePeriodTest {

    test("getYearOf") {
        getYearOf(asDate(2000, 1, 1)) should equal(2000)
        getYearOf(asDate(2000, 4, 1)) should equal(2000)
        getYearOf(2000, 12) should equal(2000)

        getYearOf(testNow) should equal(testNow.getYear)
    }

    test("next halfyear") {
        nextHalfyear(2000, Halfyear.First) should equal(YearHalfyear(2000, Halfyear.Second))
        nextHalfyear(2000, Halfyear.Second) should equal(YearHalfyear(2001, Halfyear.First))
    }

    test("prev halfyear") {
        prevHalfyear(2000, Halfyear.First) should equal(YearHalfyear(1999, Halfyear.Second))
        prevHalfyear(2000, Halfyear.Second) should equal(YearHalfyear(2000, Halfyear.First))
    }

    test("add halfyear") {
        addHalfyear(2000, Halfyear.First, 1).halfyear should equal(Halfyear.Second)
        addHalfyear(2000, Halfyear.Second, 1).halfyear should equal(Halfyear.First)

        addHalfyear(2000, Halfyear.First, -1).halfyear should equal(Halfyear.Second)
        addHalfyear(2000, Halfyear.Second, -1).halfyear should equal(Halfyear.First)

        addHalfyear(2000, Halfyear.First, 2).halfyear should equal(Halfyear.First)
        addHalfyear(2000, Halfyear.Second, 2).halfyear should equal(Halfyear.Second)

        addHalfyear(2000, Halfyear.First, -2).halfyear should equal(Halfyear.First)
        addHalfyear(2000, Halfyear.Second, -2).halfyear should equal(Halfyear.Second)

        addHalfyear(2000, Halfyear.First, 5).halfyear should equal(Halfyear.Second)
        addHalfyear(2000, Halfyear.Second, 5).halfyear should equal(Halfyear.First)

        addHalfyear(2000, Halfyear.First, -5).halfyear should equal(Halfyear.Second)
        addHalfyear(2000, Halfyear.Second, -5).halfyear should equal(Halfyear.First)

        addHalfyear(2008, Halfyear.First, 1).year should equal(2008)
        addHalfyear(2008, Halfyear.Second, 1).year should equal(2008 + 1)
        addHalfyear(2008, Halfyear.First, -1).year should equal(2008 - 1)
        addHalfyear(2008, Halfyear.Second, -1).year should equal(2008)
        addHalfyear(2008, Halfyear.First, 2).year should equal(2008 + 1)
        addHalfyear(2008, Halfyear.Second, 2).year should equal(2008 + 1)
        addHalfyear(2008, Halfyear.First, 3).year should equal(2008 + 1)
        addHalfyear(2008, Halfyear.Second, 3).year should equal(2008 + 2)
    }

    test("halfyear of month") {
        FirstHalfyearMonths.par.foreach(m => getHalfyearOfMonth(m) should equal(Halfyear.First))
        SecondHalfyearMonths.par.foreach(m => getHalfyearOfMonth(m) should equal(Halfyear.Second))
    }

    test("months of halfyear") {
        getMonthsOfHalfyear(Halfyear.First) should equal(FirstHalfyearMonths)
        getMonthsOfHalfyear(Halfyear.Second) should equal(SecondHalfyearMonths)
    }

    test("next quarter") {
        nextQuarter(2000, Quarter.First).quarter should equal(Quarter.Second)
        nextQuarter(2000, Quarter.Second).quarter should equal(Quarter.Third)
        nextQuarter(2000, Quarter.Third).quarter should equal(Quarter.Fourth)
        nextQuarter(2000, Quarter.Fourth).quarter should equal(Quarter.First)
    }

    test("prev quarter") {
        prevQuarter(2000, Quarter.First).quarter should equal(Quarter.Fourth)
        prevQuarter(2000, Quarter.Second).quarter should equal(Quarter.First)
        prevQuarter(2000, Quarter.Third).quarter should equal(Quarter.Second)
        prevQuarter(2000, Quarter.Fourth).quarter should equal(Quarter.Third)
    }

    test("add quarter") {
        addQuarter(2000, Quarter.First, 1).quarter should equal(Quarter.Second)
        addQuarter(2000, Quarter.Second, 1).quarter should equal(Quarter.Third)
        addQuarter(2000, Quarter.Third, 1).quarter should equal(Quarter.Fourth)
        addQuarter(2000, Quarter.Fourth, 1).quarter should equal(Quarter.First)

        addQuarter(2000, Quarter.First, -1).quarter should equal(Quarter.Fourth)
        addQuarter(2000, Quarter.Second, -1).quarter should equal(Quarter.First)
        addQuarter(2000, Quarter.Third, -1).quarter should equal(Quarter.Second)
        addQuarter(2000, Quarter.Fourth, -1).quarter should equal(Quarter.Third)

        addQuarter(2000, Quarter.First, 2).quarter should equal(Quarter.Third)
        addQuarter(2000, Quarter.Second, 2).quarter should equal(Quarter.Fourth)
        addQuarter(2000, Quarter.Third, 2).quarter should equal(Quarter.First)
        addQuarter(2000, Quarter.Fourth, 2).quarter should equal(Quarter.Second)

        addQuarter(2000, Quarter.First, -2).quarter should equal(Quarter.Third)
        addQuarter(2000, Quarter.Second, -2).quarter should equal(Quarter.Fourth)
        addQuarter(2000, Quarter.Third, -2).quarter should equal(Quarter.First)
        addQuarter(2000, Quarter.Fourth, -2).quarter should equal(Quarter.Second)
    }

    test("quarter of month") {
        FirstQuarterMonths.foreach(m => getQuarterOfMonth(m) should equal(Quarter.First))
        SecondQuarterMonths.foreach(m => getQuarterOfMonth(m) should equal(Quarter.Second))
        ThirdQuarterMonths.foreach(m => getQuarterOfMonth(m) should equal(Quarter.Third))
        FourthQuarterMonths.foreach(m => getQuarterOfMonth(m) should equal(Quarter.Fourth))
    }

    test("months of quarter") {
        getMonthsOfQuarter(Quarter.First) should equal(FirstQuarterMonths)
        getMonthsOfQuarter(Quarter.Second) should equal(SecondQuarterMonths)
        getMonthsOfQuarter(Quarter.Third) should equal(ThirdQuarterMonths)
        getMonthsOfQuarter(Quarter.Fourth) should equal(FourthQuarterMonths)
    }

    test("next month") {
        (1 until MonthsPerYear).par.foreach(m => nextMonth(2000, m).monthOfYear should equal(m % MonthsPerYear + 1))
    }

    test("prev month") {
        (1 until MonthsPerYear).par.foreach(m => {
            val expected = if (m <= 1) MonthsPerYear + m - 1 else m - 1
            prevMonth(2000, m).monthOfYear should equal(expected)
        })
    }

    test("add months") {
        (1 to MonthsPerYear).par.foreach(m =>
            addMonth(2000, m, 1).monthOfYear should equal(m % MonthsPerYear + 1))

        (1 to MonthsPerYear).par.foreach(m => {
            val expected = if (m <= 1) MonthsPerYear + m - 1 else m - 1
            addMonth(2000, m, -1).monthOfYear should equal(expected)
        })

        (1 to 3 * MonthsPerYear).par.foreach(m => {
            val ym = addMonth(2013, 1, m)
            ym.year should equal(2013 + m / MonthsPerYear)
            ym.monthOfYear should equal(m % MonthsPerYear + 1)
        })
    }

    test("week of year") {
        val period = TimeRange(asDate(2008, 12, 31), asDate(2014, 12, 31))
        foreachDays(period).par.foreach(p => {
            val moment = p.start
            val expected = YearWeek(moment.getWeekyear, moment.getWeekOfWeekyear)
            val actual = getWeekOfYear(moment)
            actual should equal(expected)
        })
    }

    test("dayStart") {
        dayStart(testDate) should equal(testDate.withTimeAtStartOfDay())
        dayStart(testDate).getMillisOfDay should equal(0)
        dayStart(testNow) should equal(testNow.withTimeAtStartOfDay())
        dayStart(testNow).getMillisOfDay should equal(0)
    }

    test("next day of week") {
        nextDayOfWeek(Monday) should equal(Tuesday)
        nextDayOfWeek(Tuesday) should equal(Wednesday)
        nextDayOfWeek(Wednesday) should equal(Thursday)
        nextDayOfWeek(Thursday) should equal(Friday)
        nextDayOfWeek(Friday) should equal(Saturday)
        nextDayOfWeek(Saturday) should equal(Sunday)
        nextDayOfWeek(Sunday) should equal(Monday)
    }

    test("prev day of week") {
        prevDayOfWeek(Monday) should equal(Sunday)
        prevDayOfWeek(Tuesday) should equal(Monday)
        prevDayOfWeek(Wednesday) should equal(Tuesday)
        prevDayOfWeek(Thursday) should equal(Wednesday)
        prevDayOfWeek(Friday) should equal(Thursday)
        prevDayOfWeek(Saturday) should equal(Friday)
        prevDayOfWeek(Sunday) should equal(Saturday)
    }

    test("add day of week") {

        addDayOfWeek(Monday, 6) should equal(Sunday)
        addDayOfWeek(Monday, 7) should equal(Monday)
        addDayOfWeek(Monday, 8) should equal(Tuesday)

        addDayOfWeek(Monday, 14) should equal(Monday)
        addDayOfWeek(Saturday, 14) should equal(Saturday)

        addDayOfWeek(Monday, -14) should equal(Monday)
        addDayOfWeek(Saturday, -14) should equal(Saturday)

    }
}

class TimesCompareTest extends AbstractTimePeriodTest {

    test("is same year") {
        isSameYear(testDate, testNow) should equal(false)
        isSameYear(asDate(2000, 4, 1), asDate(2000, 12, 31)) should equal(true)
        isSameYear(asDate(2000, 4, 1), asDate(2001, 1, 1)) should equal(false)
    }

    test("is same halfyear") {
        isSameHalfyear(asDate(2000, 1, 1), asDate(2000, 6, 30)) should equal(true)
        isSameHalfyear(asDate(2000, 7, 1), asDate(2000, 12, 31)) should equal(true)
        isSameHalfyear(asDate(2000, 1, 1), asDate(2000, 7, 1)) should equal(false)
        isSameHalfyear(asDate(2000, 7, 1), asDate(2001, 1, 1)) should equal(false)
    }

    test("is same quarter") {
        isSameQuarter(asDate(2000, 1, 1), asDate(2000, 3, 31)) should equal(true)
        isSameQuarter(asDate(2000, 4, 1), asDate(2000, 6, 30)) should equal(true)
        isSameQuarter(asDate(2000, 7, 1), asDate(2000, 9, 30)) should equal(true)
        isSameQuarter(asDate(2000, 10, 1), asDate(2000, 12, 31)) should equal(true)

        isSameQuarter(asDate(2000, 1, 1), asDate(2000, 4, 1)) should equal(false)
        isSameQuarter(asDate(2000, 4, 1), asDate(2000, 7, 1)) should equal(false)
        isSameQuarter(asDate(2000, 7, 1), asDate(2000, 10, 1)) should equal(false)
        isSameQuarter(asDate(2000, 10, 1), asDate(2001, 1, 1)) should equal(false)
    }

    test("is same month") {
        isSameMonth(testDate, testDiffDate) should equal(false)
        isSameMonth(asDate(2000, 10, 1), asDate(2000, 10, 31)) should equal(true)
        isSameMonth(asDate(2000, 5, 1), asDate(2000, 5, 31)) should equal(true)
        isSameMonth(asDate(2000, 10, 1), asDate(2000, 11, 1)) should equal(false)
        isSameMonth(asDate(2000, 5, 1), asDate(2000, 4, 30)) should equal(false)
    }

    test("is same week") {
        val prevWeek = testDate.minusDays(DaysPerWeek + 1)
        val nextWeek = testDate.plusDays(DaysPerWeek + 1)

        val yw = getWeekOfYear(testDate)
        val startOfWeek = getStartOfYearWeek(yw.weekyear, yw.weekOfWeekyear)

        val yw2 = getWeekOfYear(startOfWeek)

        isSameWeek(testDate, startOfWeek) should equal(true)
        isSameWeek(testDate, testDate) should equal(true)
        isSameWeek(testDiffDate, testDiffDate) should equal(true)

        isSameWeek(testDate, testDiffDate) should equal(false)
        isSameWeek(testDate, prevWeek) should equal(false)
        isSameWeek(testDate, nextWeek) should equal(false)
        isSameWeek(prevWeek, nextWeek) should equal(false)
    }

    test("is same day") {
        isSameDay(testDate, testDiffDate) should equal(false)
        isSameDay(testDate, testDate) should equal(true)
        isSameDay(asDate(2010, 10, 19), endTimeOfDay(asDate(2010, 10, 19))) should equal(true)
        isSameDay(asDate(1999, 10, 19), asDate(2000, 10, 19)) should equal(false)
        isSameDay(asDate(2000, 10, 18), asDate(2000, 10, 19)) should equal(false)
        isSameDay(asDate(2000, 10, 18), asDate(2000, 10, 17)) should equal(false)
    }

    test("is same hour") {
        isSameHour(testDate, testDiffDate) should equal(false)
        isSameHour(asDate(2000, 10, 19) + 18.hour, asDate(2000, 10, 19) + 18.hour) should equal(true)
        isSameHour(asDate(2000, 10, 19) + 18.hour, asDate(2000, 10, 19) + 18.hour + 10.minute) should equal(true)
        isSameHour(asDate(2000, 10, 19) + 18.hour, asDate(2000, 10, 19) + 1.hour) should equal(false)
    }

    test("is same minute") {
        isSameMinute(testDate, testDiffDate) should equal(false)

        isSameMinute(new DateTime(2000, 10, 19, 18, 5, 0), new DateTime(2000, 10, 19, 18, 5, 0)) should equal(true)
        isSameMinute(new DateTime(2000, 10, 19, 18, 10, 0), new DateTime(2000, 10, 19, 18, 10, 10)) should equal(true)
        isSameMinute(new DateTime(2000, 10, 19, 18, 10, 0), new DateTime(2000, 10, 19, 23, 10, 0)) should equal(false)
    }

    test("is same second") {
        isSameSecond(testDate, testDiffDate) should equal(false)

        isSameSecond(new DateTime(2000, 10, 19, 18, 5, 0), new DateTime(2000, 10, 19, 18, 5, 0)) should equal(true)
        isSameSecond(new DateTime(2000, 10, 19, 18, 10, 10), new DateTime(2000, 10, 19, 18, 10, 10)) should equal(true)
        isSameSecond(new DateTime(2000, 10, 19, 18, 10, 0), new DateTime(2000, 10, 19, 23, 10, 0)) should equal(false)
    }
}

class TimesDateTimeTest extends AbstractTimePeriodTest {

    test("get date") {
        getDate(testDate) should equal(testDate.withTimeAtStartOfDay())
        getDate(testNow) should equal(testNow.withTimeAtStartOfDay())
    }

    test("set date") {
        getDate(setDate(testDate, testNow)) should equal(testNow.withTimeAtStartOfDay())
        getDate(setDate(testNow, testDate)) should equal(testDate.withTimeAtStartOfDay())
    }

    test("has time of day") {
        hasTime(testDate) should equal(true)
        hasTime(testNow) should equal(true)
        hasTime(getDate(testNow)) should equal(false)

        hasTime(setTime(testNow, 1)) should equal(true)
        hasTime(setTime(testNow, 0, 1)) should equal(true)

        hasTime(setTime(testNow, 0, 0, 0, 0)) should equal(false)
    }

    test("set time of day") {
        hasTime(setTime(testDate, testNow)) should equal(true)
        setTime(testDate, testNow).getMillisOfDay should equal(testNow.getMillisOfDay)
        setTime(testNow, testDate).getMillisOfDay should equal(testDate.getMillisOfDay)
    }
}

class TimesForEachTest extends AbstractTimePeriodTest {

    val startTime = new DateTime(2008, 4, 10, 5, 33, 24, 345)
    val endTime = new DateTime(2010, 10, 20, 13, 43, 12, 599)

    val period = TimeRange(startTime, endTime)

    test("foreach years") {
        var count = 0
        Times.foreachYears(period).foreach(p => {
            log.trace(s"year($count)= ${p.start.getYear}")
            count += 1
        })
        count should equal(period.end.getYear - period.start.getYear + 1)
    }

    test("foreach years in same year") {
        val period = Times.relativeWeekPeriod(startTime, 1)
        val years = Times.foreachYears(period)
        years should have size 1
    }

    test("foreach months") {
        var count = 0
        Times.foreachMonths(period).foreach(p => {
            log.trace(s"month($count)=${p.start.getMonthOfYear}")
            count += 1
        })
        val months = period.getDuration.getMillis / (MillisPerDay * MaxDaysPerMonth) + 2
        count should equal(months)
    }

    test("foreach weeks") {
        var count = 0
        val weeks = Times.foreachWeeks(period)
        weeks.foreach(p => {
            log.trace(s"week($count) = [${p.start}] ~ [${p.end}], year week = ${Times.getWeekOfYear(p.start)}")
            count += 1
        })

        weeks(0).start should equal(period.start)
        weeks.last.end should equal(period.end)
    }

    test("foreach days") {
        val days = Times.foreachDays(period)
        val count = days.size

        days(0).start should equal(period.start)
        days.last.end should equal(period.end)

        log.trace(s"last-1 = ${days(days.size - 2)}")
        log.trace(s"last = ${days.last}")
    }

    test("foreach hours") {
        val hours = Times.foreachHours(period)

        hours(0).start should equal(period.start)
        hours.last.end should equal(period.end)
        hours.last.start.getMillis should be > hours(hours.size - 2).end.getMillis
    }

    test("foreach minutes") {
        val minutes = Times.foreachMinutes(period)

        minutes(0).start should equal(period.start)
        minutes.last.end should equal(period.end)
        minutes.last.start.getMillis should be > minutes(minutes.size - 2).end.getMillis
    }

    test("foreach periods") {
        val notTesting = Array(PeriodUnit.All, PeriodUnit.Second, PeriodUnit.Millisecond)

        PeriodUnit.values.foreach(unit => {
            if (!notTesting.contains(unit)) {
                Times.foreachPeriods(period, unit).par.size should be > 0
            }
        })
    }
    test("foreach periods as parallels") {
        val notTesting = Array(PeriodUnit.All, PeriodUnit.Second, PeriodUnit.Millisecond)

        PeriodUnit.values.foreach(unit => {
            if (!notTesting.contains(unit)) {
                val count = new AtomicInteger(0)
                val periods = Times.foreachPeriods(period, unit)
                periods.par.foreach(x => count.incrementAndGet())

                periods.size should equal(count.get())
            }
        })
    }
}

class TimesMathTest extends AbstractTimePeriodTest {

    val min = new DateTime(2008, 4, 10, 5, 33, 24, 345)
    val max = new DateTime(2010, 10, 20, 13, 43, 12, 599)

    test("min DateTime") {
        Times.min(min, max) should equal(min)
        Times.min(min, min) should equal(min)
        Times.min(max, max) should equal(max)

        Times.min(min, null) should equal(min)
        Times.min(null, min) should equal(min)
        Times.min(null.asInstanceOf[DateTime], null) should equal(null)
    }

    test("max DateTime") {
        Times.max(min, max) should equal(max)
        Times.max(min, min) should equal(min)
        Times.max(max, max) should equal(max)

        Times.min(min, null) should equal(min)
        Times.min(null, min) should equal(min)
        Times.min(null.asInstanceOf[DateTime], null) should equal(null)
    }

    test("adjust period") {
        val (first, second) = Times.adjustPeriod(max, min)
        first should equal(min)
        second should equal(max)

        val (m1, m2) = Times.adjustPeriod(min, max)
        m1 should equal(min)
        m2 should equal(max)
    }

    test("adjust period by duration") {
        val start = min
        val duration = Durations.Day

        val (t, d) = Times.adjustPeriod(start, duration)
        t should equal(start)
        d should equal(Durations.Day)

        val (t2, d2) = Times.adjustPeriod(start, Durations.negate(duration))
        t2 should equal(min - Durations.Day)
        d2 should equal(Durations.Day)
    }
}

class TimesPackageTest extends AbstractTimePeriodTest {

    test("date unit") {
        MonthsPerYear should equal(12)
        HalfyearsPerYear should equal(2)
        QuartersPerYear should equal(4)
        QuartersPerHalfyear should equal(2)
        MaxWeeksPerYear should equal(54)
        MonthsPerHalfyear should equal(6)
        MonthsPerQuarter should equal(3)
        MaxDaysPerMonth should equal(31)
        DaysPerWeek should equal(7)
        HoursPerDay should equal(24)
        MinutesPerHour should equal(60)
        SecondsPerMinute should equal(60)
        MillisPerSecond should equal(1000)
    }

    test("halfyear unit") {
        FirstHalfyearMonths.length should equal(MonthsPerHalfyear)
        for (i <- 0 until FirstHalfyearMonths.length) {
            FirstHalfyearMonths(i) should equal(i + 1)
        }

        SecondHalfyearMonths.length should equal(MonthsPerHalfyear)
        for (i <- 0 until SecondHalfyearMonths.length) {
            SecondHalfyearMonths(i) should equal(i + 7)
        }
    }

    test("quarter test") {
        FirstQuarterMonth should equal(1)
        SecondQuarterMonth should equal(4)
        ThirdQuarterMonth should equal(7)
        FourthQuarterMonth should equal(10)

        FirstQuarterMonths.length should equal(MonthsPerQuarter)

        for (i <- 0 until MonthsPerQuarter) {
            FirstQuarterMonths(i) should equal(i + 1)
            SecondQuarterMonths(i) should equal(i + 1 + MonthsPerQuarter)
            ThirdQuarterMonths(i) should equal(i + 1 + 2 * MonthsPerQuarter)
            FourthQuarterMonths(i) should equal(i + 1 + 3 * MonthsPerQuarter)
        }
    }

    test("duration test") {
        NoDuration should equal(Duration.ZERO)
        EmptyDuration should equal(Duration.ZERO)
        ZeroDuration should equal(Duration.ZERO)
        MinPositiveDuration should equal(Duration.millis(1))
        MinNegativeDuration should equal(Duration.millis(-1))
    }
}

class TimesPeriodTest extends AbstractTimePeriodTest {

    val PeriodCount = 24

    val startTime = new DateTime(2008, 4, 10, 5, 33, 24, 345)
    val endTime = new DateTime(2018, 10, 20, 13, 43, 12, 599)
    val duration = new Duration(startTime, endTime)

    test("TimeBlock by Duration") {
        val block = Times.getTimeBlock(startTime, duration)
        block.start should equal(startTime)
        block.end should equal(endTime)
        block.duration should equal(duration)
    }

    test("TimeBlock by start and end") {
        val block = Times.getTimeBlock(startTime, endTime)
        block.start should equal(startTime)
        block.end should equal(endTime)
        block.duration should equal(duration)
    }

    test("TimeRange by Duration") {
        val range = Times.getTimeRange(startTime, duration)
        range.start should equal(startTime)
        range.end should equal(endTime)
        range.duration should equal(duration)
    }

    test("TimeRange by start and end") {
        val range = Times.getTimeRange(startTime, endTime)
        range.start should equal(startTime)
        range.end should equal(endTime)
        range.duration should equal(duration)
    }

    test("getPeriodOf") {
        PeriodUnit.values.par
            .filter(unit => unit != PeriodUnit.All && unit != PeriodUnit.Millisecond)
            .foreach(unit => {
            val moment = startTime
            val period = Times.getPeriodOf(moment, unit)
            period.hasInside(moment) should equal(true)
            period.hasInside(endTime) should equal(false)

            log.trace(s"unit=$unit : period=$period hasInside=${period.hasInside(moment)}")
        })
    }

    test("periodOf with calendar") {
        PeriodUnit.values.par
            .filter(unit => unit != PeriodUnit.All && unit != PeriodUnit.Millisecond)
            .foreach(unit => {
            val moment = startTime
            val period = Times.getPeriodOf(moment, unit, EmptyOffsetTimeCalendar)
            period.hasInside(moment) should equal(true)
            period.hasInside(endTime) should equal(false)

            log.trace(s"unit=$unit : period=$period hasInside=${period.hasInside(moment)}")
        })
    }

    test("getPeriodsOf") {
        PeriodUnit.values.par
            .filter(unit => unit != PeriodUnit.All && unit != PeriodUnit.Millisecond)
            .foreach(unit => {

            for (count <- 1 to 5) {
                val moment = startTime
                val period = Times.getPeriodsOf(moment, unit, count, EmptyOffsetTimeCalendar)

                period.hasInside(moment) should equal(true)
                period.hasInside(endTime) should equal(false)

                log.trace(s"unit=$unit : period=$period hasInside=${period.hasInside(moment)}")
            }
        })
    }

    test("getYearRange") {
        val yr = getYearRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfYear(startTime)

        yr.start should equal(start)
        yr.startYear should equal(start.getYear)
        yr.end should equal(start + 1.year)
        yr.endYear should equal(start.getYear + 1)
    }

    test("getYearRanges") {
        (1 until PeriodCount).par.foreach(i => {
            val yrs = getYearRanges(startTime, i, EmptyOffsetTimeCalendar)
            val start = startTimeOfYear(startTime)

            yrs.start should equal(start)
            yrs.startYear should equal(start.getYear)
            yrs.end should equal(start + i.year)
            yrs.endYear should equal(start.getYear + i)
        })
    }

    test("getHalfyearRange") {
        val hy = getHalfyearRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfHalfyear(startTime)

        hy.start should equal(start)
        hy.end should equal(hy.nextHalfyear.getStart)
    }

    test("getHalfyearRanges") {
        (1 until PeriodCount).par.foreach(i => {
            val hys = getHalfyearRanges(startTime, i, EmptyOffsetTimeCalendar)
            val start = startTimeOfHalfyear(startTime)

            hys.start should equal(start)
            hys.end should equal(start + (i * MonthsPerHalfyear).month)
            hys.halfyearCount should equal(i)
        })
    }

    test("getQuarterRange") {
        val qr = getQuarterRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfQuarter(startTime)

        qr.start should equal(start)
        qr.end should equal(qr.nextQuarter.start)
    }

    test("getQuarterRanges") {
        (1 until PeriodCount).par.foreach(q => {
            val qrs = getQuarterRanges(startTime, q, EmptyOffsetTimeCalendar)
            val start = startTimeOfQuarter(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + (q * MonthsPerQuarter).month)
            qrs.quarterCount should equal(q)
        })
    }

    test("getMonthRange") {
        val mr = getMonthRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfMonth(startTime)

        mr.start should equal(start)
        mr.end should equal(mr.nextMonth.start)
    }

    test("getMonthRanges") {
        (1 until PeriodCount).par.foreach(m => {
            val qrs = Times.getMonthRanges(startTime, m, EmptyOffsetTimeCalendar)
            val start = Times.startTimeOfMonth(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + m.month)
            qrs.monthCount should equal(m)
        })
    }

    test("getWeekRange") {
        val wr = getWeekRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfWeek(startTime)

        wr.start should equal(start)
        wr.end should equal(wr.nextWeek.start)
    }

    test("getWeekRanges") {
        (1 until PeriodCount).par.foreach(w => {
            val qrs = Times.getWeekRanges(startTime, w, EmptyOffsetTimeCalendar)
            val start = Times.startTimeOfWeek(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + w.week)
            qrs.weekCount should equal(w)
        })
    }

    test("getDayRange") {
        val wr = getDayRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfDay(startTime)

        wr.start should equal(start)
        wr.end should equal(wr.nextDay.start)
    }

    test("getDayRanges") {
        (1 until PeriodCount).par.foreach(d => {
            val qrs = Times.getDayRanges(startTime, d, EmptyOffsetTimeCalendar)
            val start = Times.startTimeOfDay(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + d.day)
            qrs.dayCount should equal(d)
        })
    }

    test("getHourRange") {
        val wr = getHourRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfHour(startTime)

        wr.start should equal(start)
        wr.end should equal(wr.nextHour.start)
    }

    test("getHourRanges") {
        (1 until PeriodCount).par.foreach(h => {
            val qrs = Times.getHourRanges(startTime, h, EmptyOffsetTimeCalendar)
            val start = Times.startTimeOfHour(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + h.hour)
            qrs.hourCount should equal(h)
        })
    }

    test("getMinuteRange") {
        val wr = getMinuteRange(startTime, EmptyOffsetTimeCalendar)
        val start = startTimeOfMinute(startTime)

        wr.start should equal(start)
        wr.end should equal(wr.nextMinute.start)
    }

    test("getMinuteRanges") {
        (1 until PeriodCount).par.foreach(m => {
            val qrs = Times.getMinuteRanges(startTime, m, EmptyOffsetTimeCalendar)
            val start = Times.startTimeOfMinute(startTime)

            qrs.start should equal(start)
            qrs.end should equal(start + m.minute)
            qrs.minuteCount should equal(m)
        })
    }

}

class TimesTest extends AbstractTimePeriodTest {

    test("asString") {
        val period = TimeRange(testDate, testNow)
        val str = Times.asString(period)

        log.debug(s"period=$str")
        assert(!str.isEmpty)
    }

    test("toDateTime") {
        val dateString = testDate.toString()
        log.debug(s"dateString=$dateString")

        val parseTime = Times.toDateTime(dateString)
        parseTime.isEqual(testDate) should equal(true)

        val defaultValue = Times.toDateTime("", testNow)
        defaultValue.isEqual(testNow) should equal(true)
    }

    test("parseString") {
        val formatter = DateTimeFormat.forPattern("yyyyMMdd")
        val dateTime = formatter.parseDateTime("20131013")
        dateTime should equal(Times.asDate(2013, 10, 13))
    }
}

class TimesTimeZoneTest extends AbstractTimePeriodTest {

    test("with TimeZone") {
        val localTime = Times.now

        val utcTime = Times.asUtc(localTime)

        log.debug(s"local=$localTime, utc=$utcTime")
    }

    test("timezone id") {
        val now = Times.now
        val zoneId = now.getZone.getID
        val utc = Times.asUtc(now)

        val local = new DateTime(utc, DateTimeZone.forID(zoneId))
        log.debug(s"timezone id=$zoneId")

        local should equal(now)
    }

    test("available date time zone") {
        val utcNow = Times.nowUtc()

        DateTimeZone.getAvailableIDs.par.foreach(id => {
            val tz = DateTimeZone.forID(id)
            val tzNow = utcNow.toDateTime(tz)

            val offset = tz.getOffset(tzNow)
            log.debug(s"ID=$id, TimeZone=$tz, local=$tzNow, utc=$utcNow, offset=$offset")
        })

        val seoul = utcNow.withZone(DateTimeZone.forID("Asia/Seoul"))
        log.debug(s"utcNow=$utcNow, Seoul=$seoul")
    }

    test("local DateTime") {
        val utc = Times.nowUtc()
        val local = utc.toLocalDateTime
        local.toDateTime(DateTimeZone.UTC) should equal(utc)
    }

    test("DateTimeZone for offset millis") {
        val utcNow = Times.nowUtc()
        val localNow = utcNow.toLocalDateTime

        DateTimeZone.getAvailableIDs.par.foreach(id => {
            val tz = DateTimeZone.forID(id)
            val tzTime = utcNow.toDateTime(tz)

            // TimeZone 별 offset 값
            val offset = tz.getOffset(0)
            log.debug(s"offset=$offset, TimeZone=$tz")

            val localZone = DateTimeZone.forOffsetMillis(offset)
            val localTimeZoneTime = utcNow.toDateTime(localZone)
            localTimeZoneTime.getMillis should equal(tzTime.getMillis)
        })
    }

    test("Times get timezone offset") {
        val utcNow = Times.nowUtc
        DateTimeZone.getAvailableIDs.par.foreach(id => {
            val tz = DateTimeZone.forID(id)
            val localNow = utcNow.toDateTime(tz)

            val offset = Times.timeZoneOffset(id)
            // offset=[32400000], TimeZone=[Asia/Seoul]
            log.debug(s"offset=[$offset], TimeZone=[$tz]")

            val localZone = Times.timeZoneForOffsetMillis(offset)

            // id=[ROK], offset=[32400000], localZone=[+09:00]
            log.debug(s"id=[$id], offset=[$offset], localZone=[${localZone.getID}}]")
            val localTimeZoneTime = utcNow.toDateTime(localZone)

            localTimeZoneTime.getMillis should equal(localNow.getMillis)
        })
    }
}

class TimesTrimTest extends AbstractTimePeriodTest {

    test("trim to month") {
        Times.trimToMonth(testDate) should equal(asDate(testDate.getYear, 1, 1))

        (0 until MonthsPerYear).par.foreach(m => {
            Times.trimToMonth(testDate, m + 1) should equal(asDate(testDate.getYear, m + 1, 1))
        })
    }

    test("trim to day") {
        Times.trimToDay(testDate) should equal(asDate(testDate.getYear, testDate.getMonthOfYear, 1))

        val days = Times.getDaysInMonth(testDate.getYear, testDate.getMonthOfYear)

        (0 until days).par.foreach(day => {
            Times.trimToDay(testDate, day + 1) should equal(asDate(testDate.getYear, testDate.getMonthOfYear, day + 1))
        })
    }

    test("trim to hour") {
        Times.trimToHour(testDate) should equal(asDate(testDate))

        (0 until HoursPerDay).par.foreach(h => {
            Times.trimToHour(testDate, h) should equal(asDate(testDate) + h.hour)
        })
    }

    test("trim to minutes") {
        Times.trimToMinute(testDate) should equal(asDate(testDate).plusHours(testDate.getHourOfDay))

        (0 until MinutesPerHour).par.foreach(m => {
            Times.trimToMinute(testDate, m) should equal(asDate(testDate).plusHours(testDate.getHourOfDay) + m.minute)
        })
    }

    test("trim to seconds") {
        Times.trimToSecond(testDate) should
        equal(asDate(testDate).plusHours(testDate.getHourOfDay).plusMinutes(testDate.getMinuteOfHour))

        (0 until SecondsPerMinute).par.foreach(s => {
            Times.trimToSecond(testDate, s) should
            equal(asDate(testDate).plusHours(testDate.getHourOfDay).plusMinutes(testDate.getMinuteOfHour) + s.second)
        })
    }

    test("trim to millis") {
        Times.trimToMillis(testDate) should
        equal(testDate.withTime(testDate.getHourOfDay, testDate.getMinuteOfHour, testDate.getSecondOfMinute, 0))

        (0 until MillisPerSecond).par.foreach(ms => {
            Times.trimToMillis(testDate, ms) should
            equal(testDate.withTime(testDate.getHourOfDay, testDate.getMinuteOfHour, testDate.getSecondOfMinute, 0) + ms.millis)
        })
    }
}
