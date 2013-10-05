// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += "Play2war plugins release" at "http://repository-play-war.forge.cloudbees.com/release/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % Option(System.getProperty("play.version")).getOrElse("2.0"))

//addSbtPlugin("com.github.play2war" % "play2-war-plugin" % "1.0")  //1-SNAPSHOT")
