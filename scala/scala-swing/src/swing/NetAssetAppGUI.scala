package swing

import scala.swing._
import scala.swing.TabbedPane.Page
import java.awt.Color
import scala.swing.event.ButtonClicked
import scala.actors.{TIMEOUT, Actor}
import scala.actors.scheduler.SingleThreadedScheduler
import java.util.{Date, TimerTask}

object NetAssetAppGUI extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "Sample Net Asset GUI"

    contents = new TabbedPane {

      pages += new Page ("Tab 1", new BoxPanel(Orientation.Vertical) {

        val dateLabel = new Label { text = "Last updated: -----"}

        val valuesTable = new Table(NetAssetStockPriceHelper.getInitialTableValues, Array("Ticker", "Units", "Price", "Value", "Updated")) {
          showGrid = true
          gridColor = Color.BLACK
        }

        val updateButton = new Button { text = "Update" }
        val netAssetLabel = new Label { text = "Net Asset: ????" }

        contents += dateLabel
        contents += valuesTable
        contents += new ScrollPane(valuesTable)
        contents += updateButton
        contents += netAssetLabel

        listenTo(updateButton)

        val uiUpdater = new Actor {
          def act = {
            loop {
              reactWithin(10 * 1000) {
                case (symbol: String, units: Int, price: Double, value: Double, ts: Long) =>
                  updateTable(symbol, units, price, value, ts)
                case netAsset: Double =>
                  netAssetLabel.text = "Net Asset: " + netAsset
                  dateLabel.text = "Last Updated: " + new java.util.Date()
                  updateButton.enabled = true
                case TIMEOUT =>
                  println("WARN: timed out")
                  updateButton.enabled = true
              }
            }
          }

          override def scheduler = new SingleThreadedScheduler
        }
        uiUpdater.start()

        reactions += {
          case ButtonClicked(button) =>
            button.enabled = false
            NetAssetStockPriceHelper.fetchPrice(uiUpdater)
        }

        private def updateTable(symbol: String, units: Int, price: Double, value: Double, ts: Long): Unit = {
          for (i <- 0 until valuesTable.rowCount) {
            if (valuesTable(i, 0) == symbol) {
              valuesTable(i, 2) = price
              valuesTable(i, 3) = value
              valuesTable(i, 4) = new Date(ts).toString
            }
          }
        }

      })
    }
  }
}
