name := "HIVE1234"

version := "0.1"

scalaVersion := "2.11.12"

//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"
//libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.3.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.8"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.8"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.4.8"

// https://github.com/com-lihaoyi/requests-scala
libraryDependencies += "com.lihaoyi" %% "requests" % "0.7.0"

libraryDependencies += "com.lihaoyi" %% "ujson" % "1.4.3"

// Doesn't work :(, unless?
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.7.1"
// https://github.com/com-lihaoyi/os-lib
libraryDependencies +="com.lihaoyi" %% "os-lib" % "0.8.0"


