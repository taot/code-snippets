package com.taot.restful

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import spray.http.HttpHeaders.{`Access-Control-Allow-Methods`, `Access-Control-Allow-Headers`, `Access-Control-Allow-Origin`, Origin}
import spray.http._
import scala.Some


trait Service extends HttpService {

  object MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val userFormats = jsonFormat4(User)
  }

  import MyJsonProtocol._

  val myRoute = respondWithHeaders(`Access-Control-Allow-Origin`(AllOrigins), `Access-Control-Allow-Headers`("Content-Type", "Origin", "X-Requested-With", "X-HTTP-Method-Override", "Accept"), `Access-Control-Allow-Methods`(Seq(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE, HttpMethods.OPTIONS))) {
    respondWithMediaType(MediaTypes.`application/json`) {
      options {  // This is required by Backbonejs
        complete("OK")
      }
    }~
    path("users" / Segment) { id =>
      get {
        complete {
          println("Received GET /users/" + id)
          UserManager.get(id) match {
            case Some(u) => u
            case None => throw new ClientException("User not found by id " + id)
          }
        }
      } ~
      put {
        complete {
          println("Received PUT /users/" + id)
          UserManager.get(id).get
        }
      } ~
      delete {
        complete {
          println("Received DELETE /users/" + id)
          UserManager.get(id).get
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
//        entity(as[User]) { u =>
//          println("post invoked")
//          val nu = UserManager.add(u)
        val nu = User("Hello", "Hi", 30)
          complete(nu)
//        }
      }
    }
  }
}