package com.taot.restful

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import spray.http.HttpHeaders.{`Access-Control-Allow-Methods`, `Access-Control-Allow-Headers`, `Access-Control-Allow-Origin`, Origin}
import spray.http._
import scala.util.{Failure, Success}

trait Service extends HttpService {

  object MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val userFormats = jsonFormat4(User)
  }

  import MyJsonProtocol._

  private def getUser(id: String): Future[Option[User]] = Future {
    UserManager.get(id)
  }

  val myRoute = respondWithHeaders(`Access-Control-Allow-Origin`(AllOrigins), `Access-Control-Allow-Headers`("Content-Type", "Origin", "X-Requested-With", "X-HTTP-Method-Override", "Accept"), `Access-Control-Allow-Methods`(Seq(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE, HttpMethods.OPTIONS))) {
    respondWithMediaType(MediaTypes.`application/json`) {
      options {  // This is required by Backbonejs
        complete("OK")
      }
    }~
    path("users" / Segment) { id =>
      get {
        onSuccess(getUser(id)) { userOpt =>
          userOpt match {
            case Some(u) => complete(u)
            case None => complete("user not found by id " + id)
          }
        }
      } ~
      put {
        entity(as[User]) { u =>
          complete {
            println("Received PUT /users/" + id)
            UserManager.update(id, u)
          }
        }
      } ~
      delete {
        complete {
          println("Received DELETE /users/" + id)
          UserManager.delete(id)
          "OK"
        }
      }
    } ~
    path("users") {
      get {
        onSuccess(Future {
          UserManager.list()
        }) { list =>
          complete(list)
        }
      } ~
      post {
        entity(as[User]) { u =>
          println("post invoked")
          onSuccess(Future {
            UserManager.add(u)
          }) { nu =>
            complete(nu)
          }
        }
      }
    }
  }
}