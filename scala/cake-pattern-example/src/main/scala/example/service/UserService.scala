package example.service

import example.User
import example.dao.UserDaoComponent

trait UserServiceComponent { this: UserDaoComponent =>

  def userService: UserService

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
