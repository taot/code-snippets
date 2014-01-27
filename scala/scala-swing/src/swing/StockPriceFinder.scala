package swing

import scala.util.Random

object StockPriceFinder {

  def getLatestClosingPrice(symbol: String): Double = {
    Random.nextDouble()
  }

  def getTickersAndUnits(): Map[String, Int] = Map(
    "AAPL" -> 200,
    "ADBE" -> 125,
    "MSFT" -> 190,
    "IBM" -> 215,
    "GOOG" -> 600
  )
}
