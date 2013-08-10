package example.dao

import example.User

trait UserDaoComponent {

  def userDao: UserDao

  class UserDao {

    def create(user: User): Unit = {
      println("UserDao.create: " + user)
    }

    def delete(user: User): Unit = {
      println("UserDao.delete: " + user)
    }
  }
}
