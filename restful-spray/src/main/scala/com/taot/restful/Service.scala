package com.taot.restful

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait Service extends HttpService {

  object MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val userFormats = jsonFormat4(User)
  }

  import MyJsonProtocol._

  val myRoute = {
    pathPrefix("users" / Segment) { id =>
      get {
        onSuccess(Future {
          UserManager.get(id)
        }) { userOpt =>
          userOpt match {
            case Some(u) => complete(u)
            case None => failWith(new ClientException("User not found by id " + id))
          }
        }
      } ~
      put {
        entity(as[User]) { u =>
          val nu = UserManager.update(id, u)
          complete(nu)
        }
      } ~
      delete {
        UserManager.delete(id)
        complete("")
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
          val nu = UserManager.add(u)
          complete(nu)
        }
      }
    }
  }
}