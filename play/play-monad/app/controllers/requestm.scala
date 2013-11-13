package controllers

import play.api.mvc.{AnyContent, Request, Action}
import org.joda.time.LocalDate

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
   * 
   */
  def param(name: String): ParamString = ParamString(name)
  
  
  /*
   * String param
   */
  case class ParamString(name: String) {
    def optional = ParamStringOpt(name)
    def default(v: String) = ParamStringDefault(name, v)
    
    def asInt = ParamInt(name)
    def asLong = ParamLong(name)
    def asLocalDate = ParamLocalDate(name)
  }

  case class ParamStringOpt(name: String)
  
  case class ParamStringDefault(name: String, default: String)

  implicit def paramString2requestM(p: ParamString) = RequestM { req =>
    val param = req.getQueryString(p.name).getOrElse(throw new ParamNotFoundException(p.name))
    (param, req)
  }

  implicit def paramStringOpt2requestM(p: ParamStringOpt) = RequestM { req =>
    val paramOpt = req.getQueryString(p.name)
    (paramOpt, req)
  }
  
  implicit def paramStringDefault2requestM(p: ParamStringDefault) = RequestM { req =>
    val param = req.getQueryString(p.name).getOrElse(p.default)
    (param, req)
  }
  
  /*
   * Int param
   */
  case class ParamInt(name: String) {
    def optional = ParamIntOpt(name)
    def default(v: Int) = ParamIntDefault(name, v)
  }

  case class ParamIntOpt(name: String)
  
  case class ParamIntDefault(name: String, default: Int)

  implicit def paramInt2requestM(p: ParamInt) = RequestM { req =>
    val i = req.getQueryString(p.name) match {
      case Some(p) => Integer.parseInt(p)
      case None => throw new ParamNotFoundException(p.name)
    }
    (i, req)
  }
  
  implicit def paramIntOpt2requestM(p: ParamIntOpt) = RequestM { req =>
    val iOpt = req.getQueryString(p.name) match {
      case Some(p) => Some(Integer.parseInt(p))
      case None => None
    }
    (iOpt, req)
  }
  
  implicit def paramIntDefault2requestM(p: ParamIntDefault) = RequestM { req =>
    val i = req.getQueryString(p.name) match {
      case Some(p) => Integer.parseInt(p)
      case None => p.default
    }
    (i, req)
  }
  
  /*
   * Long param
   */
  case class ParamLong(name: String) {
    def optional = ParamLongOpt(name)
    def default(v: Long) = ParamLongDefault(name, v)
  }

  case class ParamLongOpt(name: String)
  
  case class ParamLongDefault(name: String, default: Long)

  implicit def paramLong2requestM(p: ParamLong) = RequestM { req =>
    val l = req.getQueryString(p.name) match {
      case Some(p) => java.lang.Long.parseLong(p)
      case None => throw new ParamNotFoundException(p.name)
    }
    (l, req)
  }
  
  implicit def paramLongOpt2requestM(p: ParamLongOpt) = RequestM { req =>
    val lOpt = req.getQueryString(p.name) match {
      case Some(p) => Some(java.lang.Long.parseLong(p))
      case None => None
    }
    (lOpt, req)
  }
  
  implicit def paramLongDefault2requestM(p: ParamLongDefault) = RequestM { req =>
    val l = req.getQueryString(p.name) match {
      case Some(p) => java.lang.Long.parseLong(p)
      case None => p.default
    }
    (l, req)
  }
  
  
  /*
   * LocalDate param
   */
  case class ParamLocalDate(name: String) {
    def optional = ParamLocalDateOpt(name)
    def default(v: LocalDate) = ParamLocalDateDefault(name, v)
  }

  case class ParamLocalDateOpt(name: String)
  
  case class ParamLocalDateDefault(name: String, default: LocalDate)

  implicit def paramLocalDate2requestM(p: ParamLocalDate) = RequestM { req =>
    val l = req.getQueryString(p.name) match {
      case Some(p) => LocalDate.parse(p)
      case None => throw new ParamNotFoundException(p.name)
    }
    (l, req)
  }
  
  implicit def paramLongLocalDate2requestM(p: ParamLocalDateOpt) = RequestM { req =>
    val lOpt = req.getQueryString(p.name) match {
      case Some(p) => Some(LocalDate.parse(p))
      case None => None
    }
    (lOpt, req)
  }
  
  implicit def paramLocalDateDefault2requestM(p: ParamLocalDateDefault) = RequestM { req =>
    val l = req.getQueryString(p.name) match {
      case Some(p) => LocalDate.parse(p)
      case None => p.default
    }
    (l, req)
  }

  class ParamNotFoundException(name: String) extends RuntimeException {
    
    override def getMessage(): String = "Parameter not found: " + name
  }
}