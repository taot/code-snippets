package swing

import scala.swing._
import scala.swing.event.ButtonClicked

object SampleGUI extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "A Sample Scala Swing GUI"

    val label = new Label { text = "----------------" }
    val button = new Button { text = "Click me" }

    contents = new FlowPanel {
      contents += label
      contents += button
    }

    listenTo(button)

    private var count = 0

    reactions += {
      case ButtonClicked(button) =>
        count += 1
        label.text = s"You clicked $count times"
    }
  }
}
