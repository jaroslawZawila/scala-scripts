//import $ivy.`com.typesafe.play::play-json:2.6.10`
import $ivy.`com.typesafe.play::play-json:2.6.10`

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import ammonite.ops._

import scalaj.http._
import play.api.libs.json.{JsObject, Json}

case class Event(title: String, date: LocalDate, notes: String, bunting: Boolean)
case class BankHolidays(division: String, events: List[Event])

val response: HttpResponse[String] = Http("https://www.gov.uk/bank-holidays.json").asString

implicit val re = Json.reads[Event]
implicit val r = Json.reads[BankHolidays]

val parsed = Json.parse(response.body).as[JsObject].fields.map(_._2.as[BankHolidays])

val today = LocalDate.now()

parsed.map{ d =>
  val next = d.events.filter(_.date.isAfter(today)).head
  val difference = ChronoUnit.DAYS.between(today, next.date)
  (d.division, next, difference)
}.foreach(e =>
  println(s"Next bank holiday for '${e._1}' is in ${e._3} at ${e._2.date}"))
