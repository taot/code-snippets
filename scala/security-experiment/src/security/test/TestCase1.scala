package security.test

import security.backend.Acl
import security.frontend.{AccessEntry, UserPermissions}
import security.{AclUpdater, Permission}
import org.joda.time.LocalDate

object TestCase1 {

  val acls = List.empty[Acl]

  val fePermissions = UserPermissions(
    sid = 1L,
    accessEntries = List(
      AccessEntry(
        oid = 1L,
        start = LocalDate.parse("2014-01-20"),
        end = null,
        permission = Permission.READ
      ),
      AccessEntry(
        oid = 2L,
        start = LocalDate.parse("2014-01-25"),
        end = null,
        permission = Permission.WRITE
      )
    )
  )

  def main(args: Array[String]): Unit = {
    val newAcls = AclUpdater.update(acls, fePermissions)
    println("newAcls = " + newAcls)
  }
}
