package example

import scala.math.BigDecimal
import scala.math.BigDecimal._
import java.math.{ BigDecimal => JBigDecimal }

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{ Schema, Session, SessionFactory }
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.annotations.Column

object TestSqueryl {
  def main(args: Array[String]): Unit = {
    testSqueryl
  }

  private def testSqueryl(): Unit = {
    Class.forName("com.mysql.jdbc.Driver");
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/myaccount", "root", ""),
        new MySQLAdapter))

    //testInsert
    //testUpdate
    testDelete

  }

  private def testDelete(): Unit = {
    transaction {
      SecurityMaster.accounts.deleteWhere(a => a.id >= 10)
    }
  }

  private def testInsert(): Unit = {
    val (_, d) = time {
      transaction {
        for (i <- 10 until 100) {
          val a = new Account(Some(i), Some("hello" + i),
            Some(BigDecimal(20000)))
          SecurityMaster.accounts.insert(a)
        }
      }
    }
    println("Duration: " + d + "ms")
  }

  private def testUpdate(): Unit = {
    transaction {
      update(SecurityMaster.accounts) ( s =>
        where(s.id === 1)
          set(s.capital := s.capital + 1000)
      )
      throw new Exception("hi")
      Unit
    }
  }

  private def testSelect(): Unit = {
    println("Test select")
    val (_, duration) = time {
      transaction {
        val accounts = from(SecurityMaster.accounts) (s =>
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
  @Column("id") val id: Option[Long],
  @Column("owner") val owner: Option[String],
  @Column("capital") val capital: Option[BigDecimal]
) {

  def this() = this( Some(0L), Some(""), Some(BigDecimal("0")) )
}

object SecurityMaster extends Schema {

  val accounts = table[Account]("ACCOUNT")
}
