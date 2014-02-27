package security.backend

case class Acl(
  oid: Long,
  list: List[Ace]
)