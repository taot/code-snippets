package swing

import scala.swing.{Button, MainFrame, SimpleSwingApplication}

object HelloWorld extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "Hello, world!"
    contents = new Button {
      text = "Click me"
    }
  }
}
