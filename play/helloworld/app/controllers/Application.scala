package controllers

import service.HelloService

import views._

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import javax.inject.Inject
import types.AssetClass

class Application extends Controller {

  @Inject
  private var helloService: HelloService = null

  /**
   * Describes the hello form.
   */
  val helloForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "repeat" -> number(min = 1, max = 100),
      "color" -> optional(text)
    )
  )

  // -- Actions

  /**
   * Home page
   */
  def index = Action {
    val greeting = helloService.sayHello("Terry")
    println(greeting)
    Ok(html.index(helloForm))
  }

  /**
   * Handles the form submission.
   */
  def sayHello = Action { implicit request =>
    helloForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.index(formWithErrors)),
      {case (name, repeat, color) => Ok(html.hello(name, repeat.toInt, color))}
    )
  }

  def showAssetClasses = Action {
    val ac = AssetClass.fromDbValue(1L)
    Ok(AssetClass.values().toList.toString + " " + ac)
  }

}
