name := "Scala Playground"

version := "1.0"

scalaVersion := "2.12.1"


val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "io.circe"  %% "circe-core"     % circeVersion,
  "io.circe"  %% "circe-generic"  % circeVersion,
  "io.circe"  %% "circe-parser"   % circeVersion,
  "net.databinder.dispatch" %% "dispatch-core" % "0.12.0",
  "org.scalaz" %% "scalaz-core"   % "7.2.10",
  "org.typelevel" %% "cats" % "0.9.0"
)

resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
