package security.backend

import org.joda.time.LocalDate
import security.Permission

case class Ace(
  sid: Long,
  start: LocalDate,
  end: LocalDate,
  permission: Permission,
  var aceOrder: Int = -1
) {

  def lt(e: Ace): Boolean = {
    (this.sid < e.sid) ||
      ltStartDate(this.start, e.start) ||
      ltEndDate(this.end, e.end) ||
      this.permission.compareTo(e.permission) < 0
  }

  private def ltStartDate(d1: LocalDate, d2: LocalDate): Boolean = {
    def toLong(date: LocalDate): Long = if (date == null) Long.MinValue else date.toDate().getTime
    toLong(d1) < toLong(d2)
  }

  private def ltEndDate(d1: LocalDate, d2: LocalDate): Boolean = {
    def toLong(date: LocalDate): Long = if (date == null) Long.MaxValue else date.toDate().getTime
    toLong(d1) < toLong(d2)
  }
}
