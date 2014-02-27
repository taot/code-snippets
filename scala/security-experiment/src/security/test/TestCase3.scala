package security.test

import security.backend.{Ace, Acl}
import org.joda.time.LocalDate
import security.{AclUpdater, Permission}
import security.frontend.UserPermissions

object TestCase3 {

  val acls = List(
    Acl(
      oid = 1L,
      list = List(
        Ace(
          sid = 1L,
          start = null,
          end = LocalDate.parse("2014-01-30"),
          permission = Permission.READ
        )
      )
    ),
    Acl(
      oid = 2L,
      list = List(
        Ace(
          sid = 1L,
          start = LocalDate.parse("2014-01-30"),
          end = null,
          permission = Permission.WRITE
        ),
        Ace(
          sid = 2L,
          start = null,
          end = null,
          permission = Permission.WRITE
        )
      )
    ),
    Acl(
      oid = 3L,
      list = List(
        Ace(
          sid = 3L,
          start = LocalDate.parse("2014-01-30"),
          end = null,
          permission = Permission.WRITE
        )
      )
    )
  )

  val fePermissions = UserPermissions(
    sid = 1L,
    accessEntries = List()
  )

  def main(args: Array[String]): Unit = {
    val newAcls = AclUpdater.update(acls, fePermissions)
    println("newAcls = " + newAcls)
  }
}
