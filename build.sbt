name := "rocksdb4s"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.rocksdb" % "rocksdbjni" % "6.11.4",
  "org.typelevel" %% "cats-effect" % "2.1.4",
  "co.fs2" %% "fs2-core" % "2.4.0"
)