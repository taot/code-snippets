package security.frontend

import org.joda.time.LocalDate
import security.Permission

case class AccessEntry(
  oid: Long,
  start: LocalDate,
  end: LocalDate,
  permission: Permission
)