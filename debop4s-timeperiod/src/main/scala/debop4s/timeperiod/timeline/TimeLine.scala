package debop4s.timeperiod.timeline

import debop4s.timeperiod._
import org.joda.time.DateTime
import org.slf4j.LoggerFactory


/**
 * TimePeriod의 컬렉션을 가지며, 이를 통해 여러 기간에 대한 Union, Intersection, Gap 등을 구할 수 있도록 합니다.
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 31. 오후 8:16
 */
trait ITimeLine extends Serializable {

  def periods: ITimePeriodContainer

  def limits: ITimePeriod

  def periodMapper: ITimePeriodMapper

  def combinePeriods: ITimePeriodCollection

  def intersectPeriods: ITimePeriodCollection

  def calculateGaps: ITimePeriodCollection
}

object TimeLine {

  def apply[T <: ITimePeriod](periods: ITimePeriodContainer): TimeLine[T] =
    apply(periods, null, null)

  def apply[T <: ITimePeriod](periods: ITimePeriodContainer,
                              limits: ITimePeriod): TimeLine[T] = {
    apply(periods, limits, null)
  }

  def apply[T <: ITimePeriod](periods: ITimePeriodContainer,
                              limits: ITimePeriod,
                              mapper: ITimePeriodMapper): TimeLine[T] = {
    new TimeLine[T](periods, limits, mapper)
  }
}

/**
 * debop4s.timeperiod.timeline.TimeLine
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2013. 12. 31. 오후 8:10
 */
@SerialVersionUID(8784228432548497611L)
class TimeLine[T <: ITimePeriod](private[this] val _periods: ITimePeriodContainer,
                                 private[this] val _aLimits: ITimePeriod = null,
                                 private[this] val mapper: ITimePeriodMapper = null) extends ITimeLine {

  require(_periods != null)

  private lazy val log = LoggerFactory.getLogger(getClass)

  private val _limits = if (_aLimits != null) TimeRange(_aLimits) else TimeRange(_periods)

  def periods = _periods

  def limits = _limits

  def periodMapper = mapper

  def combinePeriods: ITimePeriodCollection = {
    if (periods.size == 0)
      return new TimePeriodCollection()

    val moments = timeLineMoments
    if (moments == null || moments.size == 0)
      return TimePeriodCollection(TimeRange(this._periods))

    TimeLines.combinePeriods(moments)
  }

  def intersectPeriods: ITimePeriodCollection = {
    if (periods.size == 0)
      return new TimePeriodCollection()

    val moments = timeLineMoments
    if (moments == null || moments.size == 0)
      return new TimePeriodCollection()

    TimeLines.intersectPeriods(moments)
  }

  @inline
  def calculateGaps: ITimePeriodCollection = {
    val tpc = TimePeriodCollection()

    _periods
      .filter(x => limits.intersectsWith(x))
      .foreach {
      x => tpc.add(TimeRange(x))
    }

    val moments = timeLineMoments(tpc)
    if (moments == null || moments.size == 0)
      return TimePeriodCollection(limits)

    val range = TimeRange(mapPeriodStart(limits.start), mapPeriodEnd(limits.end))
    TimeLines.calculateGap(moments, range)
  }

  private def timeLineMoments: ITimeLineMomentCollection =
    timeLineMoments(_periods)

  @inline
  private def timeLineMoments(periods: Iterable[ITimePeriod]): ITimeLineMomentCollection = {
    val moments = TimeLineMomentCollection()
    if (periods == null || periods.size == 0)
      return moments

    // setup gap set with all start/end points
    val intersections = new TimePeriodCollection()

    periods
      .filter(!_.isMoment)
      .foreach {
      mp =>
        val intersection = limits.intersection(mp)
        if (intersection != null && !intersection.isMoment) {
          if (mapper != null) {
            intersection.setup(mapPeriodStart(intersection.start), mapPeriodEnd(intersection.end))
          }
          intersections.add(intersection)
        }
    }
    moments.addAll(intersections)
    moments
  }

  private def mapPeriodStart(moment: DateTime) =
    if (mapper != null) mapper.unmapStart(moment) else moment

  private def mapPeriodEnd(moment: DateTime) =
    if (mapper != null) mapper.unmapEnd(moment) else moment

}
