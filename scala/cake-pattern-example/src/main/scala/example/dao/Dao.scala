package example.dao

import example.{ Code, User }

trait DaoComponent {

  val codeDao: CodeDao

  val userDao: UserDao

  class CodeDao {

    def create(code: Code): Unit = {
      println("CodeDao.create: " + code)
    }

    def delete(code: Code): Unit = {
      println("CodeDao.delete: " + code)
    }
  }

  class UserDao {

    def create(user: User): Unit = {
      println("UserDao.create: " + user)
    }

    def delete(user: User): Unit = {
      println("UserDao.delete: " + user)
    }
  }
}
