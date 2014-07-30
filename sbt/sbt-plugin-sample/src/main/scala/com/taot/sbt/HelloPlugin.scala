package com.taot.sbt

import sbt._
import Keys._
import java.util.{Properties, Locale}
import java.io._
import sbt.File
import scala.collection.JavaConversions._

object HelloPlugin extends Plugin {

  import PluginKeys._

  val Settings = Seq(

    i18nMessageFile := "messages",

    i18nGen <<= inputTask {
      (argTask: TaskKey[Seq[String]]) => {
        (argTask, unmanagedResourceDirectories in Compile, managedSourceDirectories in Compile, i18nMessageFile, moduleName) map {
          (args, resourceDirs, genResourceDirs, messageFile, moduleName) => Tasks.i18nGen(args, resourceDirs, genResourceDirs, messageFile, moduleName)
        }
      }
    },

    genMessages in Compile := Tasks.generate((sourceManaged in Compile).value / "some_directory")
  )

//  override lazy val projectSettings = Seq(commands += helloCommand)

//  lazy val helloCommand = Command.command("hello") { (state: State) =>
//    println("Hi")
//
//    val provider = state.configuration.provider
//
//    val sbtScalaVersion = provider.scalaProvider.version
//    val sbtVersion = provider.id.version
//    val sbtInstance = ScalaInstance(sbtScalaVersion, provider.scalaProvider.launcher)
//    val sbtProject = BuildPaths.projectStandard(state.baseDir)
//    val sbtOut = BuildPaths.crossPath(BuildPaths.outputDirectory(sbtProject), sbtInstance)
//
//    val extracted = Project.extract(state)
//    val buildStruct = extracted.structure
//    val buildUnit = buildStruct.units(buildStruct.root)
////    val settings = Settings(extracted.currentRef, buildStruct, state)
//
//    state
//  }

}

object PluginKeys {
  lazy val i18nGen = InputKey[Unit]("i18n-gen")
  lazy val i18nMessageFile = SettingKey[String]("i18n-message-file")
  lazy val genMessages = taskKey[Seq[File]]("gen-messages")
}

object Tasks {

  def generate(dir: File): Seq[File] = {
    println(dir)
    Seq()
  }

  def i18nGen(args: Seq[String], resourceDirs: Seq[File], genResourceDirs: Seq[File], messageFile: String, moduleName: String): Unit = {
    println("args = " + args)
    println(resourceDirs)
    println(genResourceDirs)

    val pkgName = getMessagePackageName(moduleName)
    val genDir = new File(genResourceDirs(0), pkgName)

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
        genStub(file, locale, genDir, pkgName)
      }
    }
  }

  private def genStub(file: File, locale: Locale, genDir: File, pkg: String): Unit = {
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
        writer.println(s"  val ${key} = ???")
      }
      writer.println(
        """
          |
          |}
        """.stripMargin)
    } finally {
      writer.close
    }
  }

  private def getMessagePackageName(name: String): String = {
    name.split("[._-]").map(_.toLowerCase).mkString("")
  }
}