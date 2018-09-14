import ammonite.ops._

val wd = pwd

val list = ls! wd

println(list.filter(_.toString().endsWith("sbt")))


println(list)
