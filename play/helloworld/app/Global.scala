import play.api.GlobalSettings

import module.ServiceModule

import com.google.inject.Guice;
import com.google.inject.Injector;

object Global extends GlobalSettings {

  private val injector = Guice.createInjector(new ServiceModule)

  private var controllerMap = Map[Class[_], Any]()

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    println("getControllerInstance: " + controllerClass.toString + ". First check")
    controllerMap get controllerClass match {
      case Some(obj) => obj.asInstanceOf[A]
      case None =>
        synchronized {
          println("getControllerInstance: " + controllerClass.toString + ". Second check")
          controllerMap get controllerClass match {
            case Some(obj) => obj.asInstanceOf[A]
            case None =>
              println("Creating controller for " + controllerClass.toString)
              val controller = injector.getInstance(controllerClass)
              controllerMap += controllerClass -> controller
              controller
          }
        }
    }
  }
}
