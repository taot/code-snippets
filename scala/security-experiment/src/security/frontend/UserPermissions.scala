package security.frontend

case class UserPermissions(
  sid: Long,
  accessEntries: List[AccessEntry]
)