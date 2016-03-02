import pl.project13.scala.sbt.JmhPlugin

name := "macros"

organization := "com.kountable"

//scalacOptions += "-Ymacro-debug-lite"

version := "1.0-SNAPSHOT"

test in assembly := {}

scalaVersion := "2.11.7"

assemblyOption in assembly ~= { _.copy(includeScala = false).copy(includeDependency = false) }

assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case x if x.contains("META-INF/io.netty.versions.properties") => MergeStrategy.first
  case x:String => old(x)
}
}

assemblyOption in assembly ~= { _.copy(includeScala = false).copy(includeDependency = false) }

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"

libraryDependencies += "org.specs2" %% "specs2-core" % "2.4.17" % "test"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.9"

libraryDependencies += "joda-time" % "joda-time" % "2.9.2"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.2"

publishArtifact in (Compile, packageSrc) := false

// repo url here
publishTo := {
  Some("Kountable internal maven2 repo" at "http://registry.cluster.kountable.com:8080/repository/internal/")
}

publishMavenStyle := true

credentials += Credentials("Repository Archiva Managed internal Repository",
  "registry.cluster.kountable.com",
  "admin",
  "8aVgXaxwM")