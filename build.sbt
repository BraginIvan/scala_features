name := "scala_features"

//scalacOptions += "-Ymacro-debug-lite"

version := "1.0"

test in assembly := {}

scalaVersion := "2.11.7"

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

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.7"

libraryDependencies += "joda-time" % "joda-time" % "2.8.1"
