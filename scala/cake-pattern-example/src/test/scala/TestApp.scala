import example.User

import example.dao.UserDaoComponent
import example.service.UserServiceComponent

import org.mockito.Mockito
import org.specs.Specification

trait TestEnv extends UserServiceComponent with UserDaoComponent {

  val userDao = Mockito.mock(classOf[UserDao])

  val userService = Mockito.mock(classOf[UserService])
}

class TestApp extends Specification with TestEnv {

  "A UserService" should {
    "create user" in {
      val user = User("zhuoran", "123")
      userService.create(user)
    }
  }
}
