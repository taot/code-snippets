package mytest

import scala.slick.driver.MySQLDriver.simple._
import Tables._


object TestMain {

  def main(args: Array[String]): Unit = {
    val dbRef = Database.forURL("jdbc:mysql://localhost:3306/test_slick", driver = "com.mysql.jdbc.Driver")
    dbRef.withSession { implicit session =>
//      createSchema
//      populate
//      query1
//      join1
      join2
    }
  }

  def join2(implicit session: Session): Unit = {
    val q = for {
      c <- coffees if c.price < 9.0
      s <- c.supplier
    } yield (c, s)
    q foreach println
  }

  def join1(implicit session: Session): Unit = {
    val q2 = for {
      c <- coffees if c.price < 9.0
      s <- suppliers if s.id === c.supID
    } yield (c, s)
    q2 foreach println
  }

  def query2(implicit session: Session): Unit = {
    // Why not let the database do the string conversion and concatenation?
    val q1 = for(c <- coffees)
    yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
        "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
        "\t" ++ c.total.asColumnOf[String]
    // The first string constant needs to be lifted manually to a LiteralColumn
    // so that the proper ++ operator is found
    q1 foreach println
  }

  def query1(implicit session: Session): Unit = {
    coffees foreach { case (name, supID, price, sales, total) =>
      println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    }
  }

  def populate(implicit session: Session): Unit = {
    // Insert some suppliers
    suppliers += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199")
    suppliers += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460")
    suppliers += (150, "The High Ground", "100 Coffee Lane",  "Meadows",      "CA", "93966")

    // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
    coffees ++= Seq(
      ("Colombian",         101, 7.99, 0, 0),
      ("French_Roast",       49, 8.99, 0, 0),
      ("Espresso",          150, 9.99, 0, 0),
      ("Colombian_Decaf",   101, 8.99, 0, 0),
      ("French_Roast_Decaf", 49, 9.99, 0, 0)
    )
  }

  def createSchema(implicit session: Session): Unit = {
    (suppliers.ddl ++ coffees.ddl).create
  }
}
