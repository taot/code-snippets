import sbt._
import Keys._
import play.Project._

//import com.github.play2war.plugin._

object ApplicationBuild extends Build {

    val appName         = "helloworld"
    val appVersion      = "0.1"

    val appDependencies = Seq(
      "com.google.inject" % "guice" % "3.0"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
    )
    /*  // Add your own project settings here
      Play2WarPlugin.play2WarSettings: _*
    ).settings(
      Play2WarKeys.servletVersion := "3.0"
    )*/

}
