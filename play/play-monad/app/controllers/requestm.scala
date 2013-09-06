package controllers

import play.api.mvc.{AnyContent, Request, Action}

package object requestm {

  /*
   * ActionRequestM - helper function for handling Play's Action
   */

  def ActionRequestM(requestM: RequestM[play.api.mvc.Result]) = Action { request =>
    val (result, req) = requestM.run(request)
    result
  }


  /*
   * RequestM - A State Monad
   */

  case class RequestM[A](run: Request[AnyContent] => (A, Request[AnyContent])) {

    def flatMap[B](f: A => RequestM[B]): RequestM[B] = RequestM[B] { s: Request[AnyContent] =>

      val (a, ns) = this.run(s)
      val func = f(a) match {
        case RequestM(g) => g
        case _ => throw new RuntimeException("Code error")
      }
      func(ns)
    }

    def map[B](f: A => B): RequestM[B] = this.flatMap { x => unit(f(x)) }
  }

  private def unit[A](a: A): RequestM[A] = RequestM { s => (a, s) }

  /*
   * Helper classes and helper functions
   */

  // TODO complete the types: Long, Double, Date
  // TODO better error handling
  // TODO integrate with AuthAction

  def param(name: String): ParamStringOpt = ParamStringOpt(name)

  case class ParamStringOpt(name: String) {
    def asInt: ParamIntOpt = ParamIntOpt(name)
    def required: ParamString = ParamString(name)
  }

  case class ParamString(name: String)

  case class ParamIntOpt(name: String) {
    def required: ParamInt = ParamInt(name)
  }

  case class ParamInt(name: String)

  implicit def paramStringOpt2requestM(p: ParamStringOpt) = RequestM { req =>
    val paramOpt = req.getQueryString(p.name)
    (paramOpt, req)
  }

  implicit def paramString2requestM(p: ParamString) = RequestM { req =>
    val param = req.getQueryString(p.name).getOrElse(
      throw new RuntimeException("parameter " + p.name + " not found")
    )
    (param, req)
  }

  implicit def paramIntOpt2requestM(p: ParamIntOpt) = RequestM { req =>
    val iOpt = req.getQueryString(p.name) match {
      case Some(p) => Some(Integer.parseInt(p))    // TODO check NumberFormatException
      case None => None
    }
    (iOpt, req)
  }

  implicit def paramInt2requestM(p: ParamInt) = RequestM { req =>
    val i = req.getQueryString(p.name) match {
      case Some(p) => Integer.parseInt(p)         // TODO check NumberFormatException
      case None => throw new RuntimeException("parameter " + p.name + " not found")
    }
    (i, req)
  }
}