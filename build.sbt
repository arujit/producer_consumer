import scala.collection.JavaConverters._

name := "producer_consumer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "1.1.0",
  "org.json4s" %% "json4s-jackson" % "3.5.3",
  "org.scalacheck" %% "scalacheck" % "1.14.0",
  "com.github.marklister" %% "product-collections" % "1.4.5"
  //"org.json4s" %% "json4s-jackson" % "3.3.0"
  //"org.json4s"   %% "json4s-native" % "3.2.4",
  //"net.java.dev.jna" & "jna" & "3.5.2"

)
//libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.3.0"
