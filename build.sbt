name := "IO"

organization := "io.streamer"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4-SNAPSHOT"

libraryDependencies += "com.netflix.rxjava" % "rxjava-scala" % "0.20.7"
