package kr.debop4s.time

import java.sql.Timestamp
import org.joda.time.DateTime

class RichTimestamp(val self: Timestamp) extends AnyVal {
    def toDateTime: DateTime = new DateTime(self)
}
