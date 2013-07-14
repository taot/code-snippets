package scala.example.statelessrelation

import scala.math.BigDecimal
import scala.math.BigDecimal._
import java.math.{ BigDecimal => JBigDecimal }

import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column
import org.squeryl.dsl._
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{ Schema, Session, SessionFactory }

object StatelessRelation {
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

    //testLeftToRight
    testRightToLeft
  }

  private def testRightToLeft(): Unit = {
    transaction {
      val positions = from(MyAccountDb.positions) ( p => where(p.id === 1) select(p))
      positions.map { p =>
        println("Position: " + p)
        println("Account: " + p.account)
      }
    }
  }

  private def testLeftToRight(): Unit = {
    transaction {
      val accounts = from(MyAccountDb.accounts) ( a=> where(a.id === 1) select(a) )
      accounts.map { a =>
        println("Account: " + a)
        a.positions.map { p =>
          println("  Position: " + p)
        }
      }

    }
  }

  def time[R](op: => R): (R, Long) = {
    val t = System.currentTimeMillis
    val r = op
    val d = System.currentTimeMillis - t
    (r, d)
  }

}

case class Account(
  @Column("ID") val id: Long,
  @Column("OWNER") val owner: String,
  @Column("CAPITAL") val capital: BigDecimal
) extends KeyedEntity[Long] {

  def this() = this(0L, "", BigDecimal("0"))

  lazy val positions: OneToMany[Position] = MyAccountDb.accountToPositions.left(this)
}

case class Position(
  @Column("ID") val id: Long,
  @Column("ACCOUNT_ID") val accountId: Long,
  @Column("CAPITAL") val capital: BigDecimal
) {

  def this() = this(0L, 0L, BigDecimal("0"))

  lazy val account: ManyToOne[Account] = MyAccountDb.accountToPositions.right(this)
}

object MyAccountDb extends Schema {

  val accounts = table[Account]("ACCOUNT")

  val positions = table[Position]("POSITION")

  val accountToPositions = oneToManyRelation(accounts, positions).
    via( (a, p) => a.id === p.accountId)

}
