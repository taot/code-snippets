package com.taot.sbt

import sbt._
import Keys._
import java.util.{Properties, Locale}
import java.io._
import sbt.File
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object HelloPlugin extends Plugin {

  import PluginKeys._

  val Settings = Seq(

    messagesPrefix := "messages",

    sourceGenerators in Compile <+= (resourceDirectory in Compile, sourceManaged in Compile, messagesPrefix, moduleName) map {
      (resDir, destDir, msgPrefix, modName) => Tasks.i18nGen(List(resDir), destDir, msgPrefix, modName)
    }
  )

  override lazy val projectSettings = super.projectSettings ++ Settings


}

object PluginKeys {
  lazy val genMessages = taskKey[Seq[File]]("gen-messages")
  lazy val messagesPrefix = settingKey[String]("messages-prefix")
}

object Tasks {

  def i18nGen(resourceDirs: Seq[File], destDir: File, messageFile: String, moduleName: String): Seq[File] = {
    println(resourceDirs)
    println(destDir)

    val pkgName = getMessagePackageName(moduleName)
    val genDir = new File(destDir, pkgName)

    val buffer = ListBuffer.empty[File]
    for {
      dir <- resourceDirs
      file <- dir.listFiles()
      if file.name.startsWith(messageFile + ".")
    } {
      val langTag = file.name.substring(messageFile.length + 1)
      val locale = Locale.forLanguageTag(langTag)
      if (locale.getLanguage.isEmpty) {
        println("Ignore messages file with invalid name: " + file.name)
      } else {
        if (! genDir.exists) {
          genDir.mkdirs()
        }
        val outFile = genStub(file, locale, genDir, pkgName)
        buffer.append(outFile)
      }
    }
    buffer.toSeq
  }

  private def genStub(file: File, locale: Locale, genDir: File, pkg: String): File = {
    val props = new Properties()
    val reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))
    try {
      props.load(reader)
    } finally {
      reader.close()
    }

    val outputFile = new File(genDir, "R.scala")
    val writer = new PrintWriter(outputFile, "UTF-8")
    try {
      writer.println(
        s"""
          |package ${pkg}
          |
          |object R {
          |
        """.stripMargin)
      props.stringPropertyNames.foreach { key =>
        val sKey = key.replaceAll("\\.", "_")
        writer.println(s"  val ${sKey} = ???")
      }
      writer.println(
        """
          |
          |}
        """.stripMargin)
    } finally {
      writer.close
    }
    outputFile
  }

  private def getMessagePackageName(name: String): String = {
    name.split("[._-]").map(_.toLowerCase).mkString("")
  }
}