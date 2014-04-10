package com.taot.restful

import java.util.UUID

case class User(firstname: String, lastname: String, age: String, id: Option[String] = None)