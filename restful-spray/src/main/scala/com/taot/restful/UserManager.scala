package com.taot.restful

import java.util.concurrent.locks.ReentrantLock
import java.util.UUID

object UserManager {

  private val lock = new ReentrantLock

  private var users = List.empty[User]

  def list(): List[User] = users

  def get(id: String): Option[User] = {
    users.find { u => u.id.isDefined && u.id.get == id }
  }

  def add(user: User): User = withLock {
    val u = user.copy(id = Some(UUID.randomUUID().toString))
    users = u :: users
    u
  }

  def update(id: String, user: User): User = withLock {
    val rest = users.filter { u => u.id.isDefined && u.id.get != id }
    val nu = user.copy(id = Some(id))
    users = nu :: rest
    nu
  }

  def delete(id: String): Unit = withLock {
    users = users.filter { u => u.id.isDefined && u.id.get == id }
  }

  private def withLock[R](f: => R): R = {
    lock.lock()
    try {
      f
    } finally {
      lock.unlock()
    }
  }
}
