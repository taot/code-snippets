package swing

import scala.actors.{TIMEOUT, Actor}
import Actor._

object NetAssetStockPriceHelper {

  private var fetchPriceStartTime: Long = _

  val symbolsAndUnits = StockPriceFinder.getTickersAndUnits()

  def getInitialTableValues: Array[Array[Any]] = {
    val emptyArrayOfArrayOfAny = new Array[Array[Any]](0)

    (emptyArrayOfArrayOfAny /: symbolsAndUnits) { (data, element) =>
      val (symbol, units) = element
      data ++ Array(List(symbol, units, "?", "?", "?").toArray)
    }
  }

  def fetchPrice(updater: Actor): Unit = {
    fetchPriceStartTime = System.currentTimeMillis()
    actor {
      val caller = self

      symbolsAndUnits.keys.foreach { symbol =>
        actor {
          println("sleeping 3s")
          Thread.sleep(3 * 1000)
          val ts = System.currentTimeMillis()
          caller ! (symbol, StockPriceFinder.getLatestClosingPrice(symbol), ts)
        }
      }

      val netWorth = (0.0 /: (1 to symbolsAndUnits.size)) { (worth, index) =>
        receiveWithin(10000) {
          case (symbol: String, latestClosingPrice: Double, ts: Long) =>
            val units = symbolsAndUnits(symbol)
            val value = units * latestClosingPrice
            updater ! (symbol, units, latestClosingPrice, value, ts)
            worth + value
          case TIMEOUT =>
            println("Timed out on receiving closing price")
            0
        }
      }

      val duration = System.currentTimeMillis() - fetchPriceStartTime
      println(s"Duration fetching price: $duration ms")

      updater ! netWorth
    }
  }
}
