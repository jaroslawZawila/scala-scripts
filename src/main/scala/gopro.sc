import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}
//import $ivy.`joda-time:joda-time:2.10`


import ammonite.ops._

val path = root/'home/'jzawila/'Videos/"Snowdonia"

val video = path/'video
val pictures = path/'pictures
val low = path/'low

mkdir! video
mkdir! pictures
mkdir! low

val files = ls! path

files.toList.foreach { path =>
  if(path.isFile) {
    path.segments.last match {
      case f if f.endsWith(".JPG") => mv.into(path, pictures)
      case f if f.endsWith(".MP4") => mv.into(path, video)
      case f if f.endsWith(".THM") => rm(path)
      case f if f.endsWith(".LRV") => mv.into(path, low)
      case f => println(s"UNKNOWN: $f")
    }
  }
}

val p = ls! pictures

p.filter(_.isFile).foreach{p =>
  val t = p.mtime
  val f = Instant.ofEpochMilli(t.toMillis).atZone(ZoneId.systemDefault()).toLocalDate().toString
  mkdir! pictures/f

  mv.into(p, pictures/f)
}

val v = ls! video

v.filter(_.isFile).foreach{p =>
  val t = p.mtime
  val f = Instant.ofEpochMilli(t.toMillis).atZone(ZoneId.systemDefault()).toLocalDate().toString
  mkdir! video/f

  mv.into(p, video/f)
}

val l = ls! low

l.filter(_.isFile).foreach{p =>
  val t = p.mtime
  val f = Instant.ofEpochMilli(t.toMillis).atZone(ZoneId.systemDefault()).toLocalDate().toString
  mkdir! low/f

  mv.into(p, low/f)
}
