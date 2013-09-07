package example.service

import example.{ Code, User }
import example.dao.DaoComponent

trait ServiceComponent { this: DaoComponent =>

  val codeService: CodeService

  val userService: UserService

  class CodeService {

    def create(code: Code): Unit = {
      println("CodeService.create: " + code)
      codeDao.create(code)
    }

    def delete(code: Code): Unit = {
      println("CodeService.delete: " + code)
      codeDao.delete(code)
    }
  }

  class UserService {

    def create(user: User): Unit = {
      println("UserService.create: " + user)
      userDao.create(user)
    }

    def delete(user: User): Unit = {
      println("UserService.delete: " + user)
      userDao.delete(user)
    }
  }
}
