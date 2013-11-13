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
      callbackOpt <- param("callback").optional
      n <- param("n").asInt.default(1)
      l <- param("l").asLong
      date <- param("asOfDate").asLocalDate
    } yield {
      Ok("callback: " + callbackOpt + ", n: " + n + ", l: " + l + ", date: " + date)
    }
  }
}
