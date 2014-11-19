package test

import java.sql.Connection
import javax.sql.DataSource

import Constants._

class TransactionProcessor(ds: DataSource) {

  def process(tx: Transaction): Unit = {
    // get connection
    val conn = ds.getConnection
    conn.setAutoCommit(false)

    // load cash
    val (cash, payable, receivable, cashExists) = loadCash(conn, tx.accountId)
//    println(s"cash = ${cash}, payable = ${payable}, receivable = ${receivable}")

    // load position
    val (quantity, carryingValue, positionId) = loadPosition(conn, tx.accountId, tx.securityId)
//    println(s"quantity = ${quantity}, carryingValue = ${carryingValue}")

    // update cash
    updateCash(conn, tx.accountId, cash, payable + tx.amount, receivable, cashExists)

    // update position
    updatePosition(conn, tx.accountId, tx.securityId, quantity + tx.quantity, carryingValue + tx.amount, positionId)

    // close connect
    conn.commit()
    conn.close()
  }

  private def updatePosition(conn: Connection, accountId: Long, securityId: Long, quantity: BigDecimal, carryingValue: BigDecimal, positionId: Long): Unit = {
    if (positionId > 0) {
      val stmt = conn.prepareStatement("update positions set quantity = ?, carrying_value = ? where id = ?")
      stmt.setBigDecimal(1, quantity.bigDecimal)
      stmt.setBigDecimal(2, carryingValue.bigDecimal)
      stmt.setLong(3, positionId)
      stmt.executeUpdate()
      stmt.close()
    } else {
      val stmt = conn.prepareStatement("insert into positions (account_id, security_id, quantity, carrying_value) values (?, ?, ?, ?)")
      stmt.setLong(1, accountId)
      stmt.setLong(2, securityId)
      stmt.setBigDecimal(3, quantity.bigDecimal)
      stmt.setBigDecimal(4, carryingValue.bigDecimal)
      stmt.executeUpdate()
      stmt.close()
    }
  }

  private def updateCash(conn: Connection, accountId: Long, cash: BigDecimal, payable: BigDecimal, receivable: BigDecimal, toUpdate: Boolean): Unit = {
    if (toUpdate) {
      val stmt = conn.prepareStatement("update cash set cash = ?, payable = ?, receivable = ? where account_id = ?")
      stmt.setBigDecimal(1, cash.bigDecimal)
      stmt.setBigDecimal(2, payable.bigDecimal)
      stmt.setBigDecimal(3, receivable.bigDecimal)
      stmt.setLong(4, accountId)
      stmt.executeUpdate()
      stmt.close()
    } else {
      val stmt = conn.prepareStatement("insert into cash (account_id, cash, margin, payable, receivable) values (?, ?, ?, ?, ?)")
      stmt.setLong(1, accountId)
      stmt.setBigDecimal(2, cash.bigDecimal)
      stmt.setBigDecimal(3, ZERO.bigDecimal)
      stmt.setBigDecimal(4, payable.bigDecimal)
      stmt.setBigDecimal(5, receivable.bigDecimal)
      stmt.executeUpdate()
      stmt.close()
    }
  }

  private def loadPosition(conn: Connection, accountId: Long, securityId: Long): (BigDecimal, BigDecimal, Long) = {
    val stmt = conn.prepareStatement("select id, quantity, carrying_value from positions where account_id = ? and security_id = ?")
    stmt.setLong(1, accountId)
    stmt.setLong(2, securityId)
    val rs = stmt.executeQuery()

    while (rs.next()) {
      val id = rs.getLong("id")
      val quantity = rs.getBigDecimal("quantity")
      val carryingValue = rs.getBigDecimal("carrying_value")
      rs.close()
      stmt.close()
      return (BigDecimal(quantity), BigDecimal(carryingValue), id)
    }

    rs.close()
    stmt.close()
    return (ZERO, ZERO, -1)
  }

  private def loadCash(conn: Connection, accountId: Long): (BigDecimal, BigDecimal, BigDecimal, Boolean) = {
    val stmt = conn.prepareStatement("select cash, payable, receivable from cash where account_id = ?")
    stmt.setLong(1, accountId)
    val rs = stmt.executeQuery()

    while (rs.next()) {
      val cash = rs.getBigDecimal("cash")
      val payable = rs.getBigDecimal("payable")
      val receivable = rs.getBigDecimal("receivable")
      rs.close()
      stmt.close()
      return (BigDecimal(cash), BigDecimal(payable), BigDecimal(receivable), true)
    }

    rs.close()
    stmt.close()
    return (ZERO, ZERO, ZERO, false)
  }
}
