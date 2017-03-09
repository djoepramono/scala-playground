name := "Scala Playground"

version := "1.0"

scalaVersion := "2.12.1"


val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "io.circe"  %% "circe-core"     % circeVersion,
  "io.circe"  %% "circe-generic"  % circeVersion,
  "io.circe"  %% "circe-parser"   % circeVersion
)
