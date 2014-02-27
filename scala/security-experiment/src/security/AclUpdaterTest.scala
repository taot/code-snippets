package security

import security.backend.{Ace, Acl}
import security.backend.Ace
import security.frontend.{AccessEntry, UserPermissions}


object AclUpdaterTest {

  val acls = List(
    Acl(
      oid = 1L,
      list = List(
        Ace(sid = 1L, start = null, end = null, permission = Permission.READ),
        Ace(sid = 1L, start = null, end = null, permission = Permission.WRITE),
        Ace(sid = 2L, start = null, end = null, permission = Permission.READ),
        Ace(sid = 3L, start = null, end = null, permission = Permission.EXECUTE)
      )
    ),
    Acl(
      oid = 2L,
      list = List(
        Ace(sid = 1L, start = null, end = null, permission = Permission.READ),
        Ace(sid = 2L, start = null, end = null, permission = Permission.READ)
      )
    ),
    Acl(
      oid = 3L,
      list = List(
        Ace(sid = 2L, start = null, end = null, permission = Permission.READ),
        Ace(sid = 2L, start = null, end = null, permission = Permission.WRITE)
      )
    )
  )

  val fePermissions = UserPermissions(
    sid = 2L,
    accessEntries = List(
      AccessEntry(
        oid = 1L,
        start = null,
        end = null,
        permission = Permission.READ
      ),
      AccessEntry(
        oid = 1L,
        start = null,
        end = null,
        permission = Permission.WRITE
      ),
      AccessEntry(
        oid = 2L,
        start = null,
        end = null,
        permission = Permission.READ
      ),
      AccessEntry(
        oid = 3L,
        start = null,
        end = null,
        permission = Permission.READ
      )
    )
  )

  def main(args: Array[String]): Unit = {
    val updated = AclUpdater.update(acls, fePermissions)
    println(updated)
  }
}
