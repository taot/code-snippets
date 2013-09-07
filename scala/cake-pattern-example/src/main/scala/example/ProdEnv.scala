package example

import example.service.ServiceComponent
import example.dao.DaoComponent

trait ProdEnv extends ServiceComponent with DaoComponent {

  val codeDao = new CodeDao

  val userDao = new UserDao

  val userService = new UserService

  val codeService = new CodeService
}

object BusinessLogic extends ProdEnv {

  def run(): Unit = {
    userService.create(User("zhuoran", "123"))
    codeService.create(Code("zhuoran"))
    userDao.delete(User("hello", "1234"))
  }
}
