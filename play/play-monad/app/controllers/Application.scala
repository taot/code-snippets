package controllers

import play.api._
import play.api.mvc._

import controllers.requestm._

object Application extends Controller {
  
  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  def test = ActionRequestM {
    for {
      callback <- param("callback").required
      n <- param("n").asInt
    } yield {
      Ok("callback: " + callback + "  n: " + n + "  n.class: " + n.getClass)
    }
  }
}