name := "chirper"

version := "1.0"

scalaVersion := "2.11.6"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "org.mongodb" % "mongodb-driver" % "3.0.2"
)
