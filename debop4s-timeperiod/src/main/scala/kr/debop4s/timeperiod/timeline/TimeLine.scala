package kr.debop4s.timeperiod.timeline

import java.util
import kr.debop4s.core.logging.Logger
import kr.debop4s.timeperiod._
import org.joda.time.DateTime
import scala.collection.JavaConversions._

/**
 * kr.debop4s.timeperiod.timeline.TimeLine
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 31. 오후 8:10
 */
@SerialVersionUID(8784228432548497611L)
class TimeLine[T <: ITimePeriod](val periods: ITimePeriodContainer,
                                 private val aLimits: ITimePeriod = null,
                                 private val mapper: ITimePeriodMapper = null) extends ITimeLine {

    lazy val log = Logger[TimeLine[_]]

    val limits = if (aLimits != null) TimeRange(aLimits) else TimeRange(periods)

    def getPeriod = periods

    def getLimits = limits

    def getPeriodMapper = mapper

    def combinePeriods: ITimePeriodCollection = {
        if (periods.size == 0)
            new TimePeriodCollection()

        val moments = getTimeLineMoments
        if (moments == null || moments.size == 0)
            new TimePeriodCollection(TimeRange(periods))

        TimeLines.combinePeriods(moments)
    }

    def intersectPeriods: ITimePeriodCollection = {
        if (periods.size == 0)
            new TimePeriodCollection()

        val moments = getTimeLineMoments
        if (moments == null || moments.size == 0)
            new TimePeriodCollection()

        TimeLines.intersectPeriods(moments)
    }

    def calculateGaps: ITimePeriodCollection = {
        val gapPeriods = new TimePeriodCollection()

        periods
            .filter(x => limits.intersectsWith(x))
            .foreach(x => gapPeriods.add(TimeRange(x)))

        val moments = getTimeLineMoments(gapPeriods)
        if (moments == null || moments.size == 0)
            new TimePeriodCollection(limits)

        val range = TimeRange(mapPeriodStart(limits.getStart), mapPeriodEnd(limits.getEnd))
        TimeLines.calculateGap(moments, range)
    }

    private def getTimeLineMoments: ITimeLineMomentCollection = getTimeLineMoments(periods)

    private def getTimeLineMoments(periods: util.Collection[_ <: ITimePeriod]): ITimeLineMomentCollection = {
        log.trace("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드합니다...")

        val moments = new TimeLineMomentCollection()
        if (periods == null || periods.size == 0) return moments

        // setup gap set with all start/end points
        //
        val intersections = new TimePeriodCollection()
        periods.foreach(mp => {
            if (!mp.isMoment) {
                val intersection = limits.getIntersection(mp)
                if (intersection != null && !intersection.isMoment) {
                    if (mapper != null) {
                        intersection.setup(mapPeriodStart(intersection.getStart),
                                           mapPeriodEnd(intersection.getEnd))
                    }
                    intersections.add(intersection)
                }
            }
        })
        moments.addAll(intersections)
        log.trace("기간 컬렉션으로부터 ITimeLineMoment 컬렉션을 빌드했습니다. moments=[{}]", moments)
        moments
    }

    private def mapPeriodStart(moment: DateTime) =
        if (mapper != null) mapper.unmapStart(moment) else moment

    private def mapPeriodEnd(moment: DateTime) =
        if (mapper != null) mapper.unmapEnd(moment) else moment

}
