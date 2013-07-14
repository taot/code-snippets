package scala.example.basic

import scala.math.BigDecimal
import scala.math.BigDecimal._
import java.math.{ BigDecimal => JBigDecimal }

import org.squeryl.dsl
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{ Schema, Session, SessionFactory }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column

object BasicFeatures {
  def main(args: Array[String]): Unit = {
    Class.forName("com.mysql.jdbc.Driver");
    SessionFactory.concreteFactory = Some { () =>
      val session = Session.create(
        java.sql.DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/myaccount", "root", ""),
        new MySQLAdapter)
      session.setLogger( statement => println("GENERATED SQL: " + statement) )
      session
    }

    testSelectOuterJoinSum
    //testSelectOuterJoin
    //testSelectJoin
    //testSelect
    //testInsert
    //testUpdate
    //testDelete
  }

  private def testSelectOuterJoinSum(): Unit = {
    import SecurityMaster._
    transaction {
      val accountCapitals = join(ACCOUNTS, POSITIONS.leftOuter)((acc, pos) =>
        groupBy(acc.id, acc.owner)
          compute( nvl(sum(pos.map(_.capital)), 0) )
          on (acc.id === pos.map(_.accountId))
      )
      for (accCap <- accountCapitals) {
        println(accCap.key + "  " + accCap.measures)
      }
    }
  }

  private def testSelectOuterJoin(): Unit = {
    import SecurityMaster._
    transaction {
      val accountsPositions = join(ACCOUNTS, POSITIONS.leftOuter)((acc, pos) =>
        select(acc, pos)
          on (acc.id === pos.map(_.accountId))
      )
      for (accPos <- accountsPositions) {
        val (acc, posOpt) = accPos
        val builder = new StringBuilder
        builder.append("AccountOwner=" + acc.owner)
        builder.append("  AccountId=" + acc.id)
        posOpt match {
          case Some(pos) =>
            builder.append("  PositionId=" + pos.id)
            builder.append("  PositionCapital=" + pos.capital)
          case None =>
        }
        println(builder.toString)
      }
    }
  }

  private def testSelectJoin(): Unit = {
    import SecurityMaster._
    transaction {
      val poss = from(ACCOUNTS, POSITIONS) ( (acc, pos) =>
        where(acc.id === pos.accountId)
          select(acc, pos)
          orderBy(acc.id asc, pos.id desc)
      )
      for (p <- poss) {
        val (acc, pos) = p
        println(acc.owner + " " + acc.id + " " + pos.id + " " + pos.capital)
        //println(p)
      }
    }
  }

  private def testDelete(): Unit = {
    transaction {
      SecurityMaster.ACCOUNTS.deleteWhere(a => a.id.~ >= 10)
    }
  }

  private def testInsert(): Unit = {
    val (_, d) = time {
      transaction {
        for (i <- 10 until 100) {
          val a = new Account(i, "hello" + i, BigDecimal(20000))
          SecurityMaster.ACCOUNTS.insert(a)
        }
      }
    }
    println("Duration: " + d + "ms")
  }

  private def testUpdate(): Unit = {
    transaction {
      update(SecurityMaster.ACCOUNTS)(s =>
        where(s.id === 1)
          set (s.capital := s.capital + 1000))
      throw new Exception("hi")
      Unit
    }
  }

  private def testSelect(): Unit = {
    println("Test select")
    val (_, duration) = time {
      transaction {
        val accounts = from(SecurityMaster.ACCOUNTS) ( s =>
          select(s)
        )
        for (a <- accounts) {
          println(a.id)
        }
      }
    }
    println("Duration: " + duration + "ms")
  }

  def time[R](op: => R): (R, Long) = {
    val t = System.currentTimeMillis
    val r = op
    val d = System.currentTimeMillis - t
    (r, d)
  }

}

class Account(
  @Column("ID") val id: Long,
  @Column("OWNER") val owner: String,
  @Column("CAPITAL") val capital: BigDecimal
) {
  def this() = this(0L, "", BigDecimal("0"))
}

class Position(
  @Column("ID") val id: Long,
  @Column("ACCOUNT_ID") val accountId: Long,
  @Column("CAPITAL") val capital: BigDecimal
) {
  def this() = this(0L, 0L, BigDecimal("0"))
}

object SecurityMaster extends Schema {

  val ACCOUNTS = table[Account]("ACCOUNT")

  val POSITIONS = table[Position]("POSITION")
}
