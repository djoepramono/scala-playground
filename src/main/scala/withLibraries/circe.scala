package net.studikode.scala.withLibraries

import io.circe.generic.auto._
import io.circe.parser._

/**
 * As of Scala 12, you need the following in your .sbt
 * - circe-core 0.7.0
 * - circe-generi 0.7.0
 * - circe-parser 0.7.0
 */

 /**
  * First you need to set your case classes
  */
 case class Contestant(
   id: Int,
   name: String,
   team: Team
 )

 case class Team(
   id: String,
   name: String
 )

 /**
  * Second you need a decoder
  * Which is basically trying to put the whole json string into
  *  the corresponding case class
  */
 object MyDecoder {
   def decodeJson(json: String) = decode[Contestant](json)
 }

/**
 * And of course the application itself :)
 */
object Circe {
  def main(args: Array[String]) = {

    val json =
      """
        | {
        |   "id": 1,
        |   "name": "John",
        |   "team": {
        |     "id": "TEAM01",
        |     "name": "Big Winners"
        |   }
        | }
      """.stripMargin

    val decoded = MyDecoder.decodeJson(json)
    println(decoded)

    val contestant: Contestant = decoded match {
      case Left(e) => Contestant(0,"",Team("","")) // Can be improved ...
      case Right(c) => c
    }

    println(s"${contestant.name} team name is ${contestant.team.name}")
  }
}
