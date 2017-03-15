package net.studikode.scala.withLibraries

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.Json

/**
 * As of Scala 12, you need the following in your .sbt
 * - circe-core 0.7.0
 * - circe-generi 0.7.0
 * - circe-parser 0.7.0
 */

 /**
  * First you need to set your case classes
  * The case classes may cover only a subset of the whole to-be-decoded JSON
  */
 case class Contestant(
   first_name: String,
   last_name: String
 )

 case class Team(
   id: Int,
   name: String,
   motto: String,
   members: List[Contestant]
 )

 /**
  * Second you need a decoder
  * Which is basically trying to put the whole json string into
  *  the corresponding case class
  */
 object MyDecoder {
   def decodeJson(json: String) = decode[Team](json)
 }

/**
 * And of course the application itself :)
 */
object Circe {
  def main(args: Array[String]) = {

    val json =
      """
      {
        "id": 1,
        "name": "Big Winners",
        "motto": "Win Big or Go Home",
        "members": [
          {
            "first_name": "John",
            "last_name": "Doe",
            "score": 75
          },
          {
            "first_name": "Mary",
            "last_name": "Jane",
            "score:": 80
          }
        ]
      }
      """.stripMargin


    // If you want the whole lot, you can decode the whole JSON
    val decoded = MyDecoder.decodeJson(json)
    decoded match {
      case Left(e) => println(e.getMessage)
      case Right(t) => println(s"Decoded successfully ${t}")
    }

    // Or you can focus a section you want and decode
    //   the one you want
    val result = parse(json)
    val jsonR = result.getOrElse(Json.Null)
    val cursor = jsonR.hcursor

    val firstMember = cursor.downField("members").downArray.focus
    println(firstMember)

    firstMember match {
      case None => println("Cannot focus")
      case Some(v) => decode[Contestant](v.toString) match {
        case Left(e) => println("Cannot decode")
        case Right(v) => println(s"${v.first_name} ${v.last_name} found")
      }
    }

  }
}
