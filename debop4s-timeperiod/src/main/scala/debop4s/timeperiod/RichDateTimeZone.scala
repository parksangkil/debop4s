package debop4s.timeperiod

import java.util.Locale
import org.joda.time.{ReadableInstant, DateTimeZone}

/**
 * com.github.time.RichDateTimeZone
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since  2014. 1. 6. 오후 8:30
 */
class RichDateTimeZone(val self: DateTimeZone) extends AnyVal {

  def id: String = self.getID

  def name(instant: Long): String = self.getName(instant)

  def name(instant: Long, locale: Locale): String = self.getName(instant, locale)

  def offset(instant: Long): Int = self.getOffset(instant)

  def offset(instant: ReadableInstant): Int = self.getOffset(instant)

  def offsetFromLocal(instantLocal: Long) = self.getOffsetFromLocal(instantLocal)

  def standardOffset(instant: Long): Int = self.getStandardOffset(instant)

  def isStandardOffset(instant: Long) = self.standardOffset(instant)

  def nameKey(instant: Long): String = self.getNameKey(instant)

  def shortName(instant: Long): String = self.getShortName(instant)

}
