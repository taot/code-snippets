package example

import example.dao.UserDaoComponent
import example.service.UserServiceComponent

object UserModule extends UserServiceComponent with UserDaoComponent {

  val userDao = new UserDao

  val userService = new UserService

  def test(username: String): Unit = {
    val password = scala.util.Random.nextInt(10000).toString
    val user = User(username, password)
    userService.create(user)
    userService.delete(user)
  }
}
