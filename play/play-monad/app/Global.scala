import play.api.GlobalSettings

object Global extends GlobalSettings {

  override def onStart(app: play.api.Application): Unit = {
    app.routes match {
      case Some(routes) =>
        println("routes: " + routes.)
        println(routes.documentation)
      case None =>
        println("routes: None")
    }
  }
}
