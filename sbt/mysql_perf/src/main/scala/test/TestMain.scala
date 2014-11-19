package test

import java.util.Properties
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

object TestMain {

  def main(args: Array[String]): Unit = {
    println("Start")
    val ds = getDataSource()
    val processor = new TransactionProcessor(ds)

    val start = System.currentTimeMillis()

    for (i <- 0 until 3000) {
      val tx = new Transaction(1L, 1L, 10, 125)
      processor.process(tx)

      if (i % 50 == 0) {
        println("Iteration " + i)
      }
    }

    val duration = System.currentTimeMillis() - start
    println("Duration: " + (duration / 1000.0))
  }

  private def getDataSource(): DataSource = {
    val props = new Properties

    props.setProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
    props.setProperty("dataSource.url", "jdbc:mysql://localhost:3306/test_perf")
    props.setProperty("dataSource.user", "root")
    props.setProperty("dataSource.password", "")

    props.setProperty("dataSource.cachePrepStmts", "true")
    props.setProperty("dataSource.prepStmtCacheSize", "250")
    props.setProperty("dataSource.prepStmtCacheSqlLimit", "2048")
    props.setProperty("dataSource.useServerPrepStmts", "true")

    val config = new HikariConfig(props)
    config.setPoolName("test_transaction_pool")
    config.setRegisterMbeans(true)
    val ds = new HikariDataSource(config)

    ds
  }
}
