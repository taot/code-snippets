package com.taot.restful

import akka.actor.Actor
import spray.routing.ExceptionHandler
import spray.http.StatusCodes._
import spray.util.LoggingContext

class ServiceActor extends Actor with Service {

  implicit def myExceptionHandler(implicit log: LoggingContext) = ExceptionHandler {
    case e: ClientException =>
      requestUri { uri =>
        log.warning("Request to {} could not be handled normally", uri)
        complete(BadRequest, "Client error: " + e.getMessage)
      }
  }

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}