package security

import security.frontend.{AccessEntry, UserPermissions}
import security.backend.{Ace, Acl}
import scala.collection.mutable

object AclUpdater {

  def update(existingAcls: Seq[Acl], fePermissions: UserPermissions): Seq[Acl] = {
    val resultAcls = mutable.ListBuffer.empty[Acl]
    val updatedAcls = mutable.ListBuffer.empty[Acl]

    if (existingAcls == null || existingAcls.isEmpty) {
      return createAcls(fePermissions)
    }

    for (acl <- existingAcls) {
      updateAcl(acl, fePermissions) match {
        case Some(a) =>
          updatedAcls.append(a)
          resultAcls.append(a)
        case None =>
          resultAcls.append(acl)
      }
    }
    // go through updatedAcls, delete updated acls and re-add

    println("AclUpdater.update: updatedAcls = " + updatedAcls)

    resultAcls.toList.sortBy(_.oid)
  }

  private def createAcls(fePermissions: UserPermissions): Seq[Acl] = {
    val acls = fePermissions.accessEntries.groupBy(_.oid) map { case (oid, entries) =>
      val aces = entries.map(IntermediateAce(_, oid).toAce())
      val sorted = aces.sortWith(_ lt _)
      Acl(oid, sorted)
    }
    acls.toSeq.sortBy(_.oid)
  }

  private def updateAcl(acl: Acl, fePermissions: UserPermissions): Option[Acl] = {
    val list1 = acl.list.filter(_.sid == fePermissions.sid).map(IntermediateAce(_, acl.oid))
    val list2 = fePermissions.accessEntries.filter(_.oid == acl.oid).map(IntermediateAce(_, fePermissions.sid))
    val sorted1 = list1.sortWith(_ lt _)
    val sorted2 = list2.sortWith(_ lt _)
    if (sorted1 == sorted2) {
      // no need to update
      None
    } else {
      // update with new permissions
      val aces = list2.map(_.toAce())
      val rest = acl.list.filterNot(_.sid == fePermissions.sid)
      val sortedNewAces = (aces ++ rest).sortWith(_ lt _)
      Some(Acl(acl.oid, sortedNewAces))
    }
  }
}
