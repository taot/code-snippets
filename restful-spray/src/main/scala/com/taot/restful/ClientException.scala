package com.taot.restful

class ClientException(message: String, cause: Throwable) extends RuntimeException(message, cause) {

  def this(message: String) = this(message, null)
}
