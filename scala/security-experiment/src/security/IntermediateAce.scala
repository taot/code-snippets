package security

import org.joda.time.LocalDate
import security.backend.Ace
import security.frontend.AccessEntry

case class IntermediateAce(
  sid: Long,
  oid: Long,
  start: LocalDate,
  end: LocalDate,
  permission: Permission
) {

  def toAce(): Ace = Ace(sid, start, end, permission)

  def lt(e: IntermediateAce): Boolean = {
    (this.sid < e.sid) ||
      (this.oid < e.oid) ||
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

object IntermediateAce {

  def apply(ace: Ace, oid: Long): IntermediateAce =
    IntermediateAce(sid = ace.sid, oid = oid, start = ace.start, end = ace.end, permission = ace.permission)

  def apply(ent: AccessEntry, sid: Long): IntermediateAce =
    IntermediateAce(sid = sid, oid = ent.oid, start = ent.start, end = ent.end, permission = ent.permission)
}