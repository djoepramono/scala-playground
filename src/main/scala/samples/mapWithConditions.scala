package net.studikode.scala.samples

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Await}
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success, Try }

sealed trait Membership
case object Premium extends Membership
case object Standard extends Membership

sealed trait Perk
case object FreeDrink extends Perk
case object EarlyAccess extends Perk

case class Patron (name: String, membership: Option[Membership])
case class CheckResult (name: String, isBlacklisted: Boolean, freebies: List[Perk]) {
  def print: String = {
    s"Checking: ${name}, Blacklisted? ${isBlacklisted}, Freebies: ${freebies}"
  }
}

object MapNWithConditions {

  val ec = ExecutionContext.global

  def checkMembership(m:Membership): Future[List[Perk]] =
    Future {
      m match {
        case Premium => List(FreeDrink, EarlyAccess)
        case Standard => List(FreeDrink)
        case _ => List()
    }
  }

  def checkBlackList(n:String): Future[Boolean] = {
    // Future {(n == "Jerry")}
    Future {throw new Exception("Rawr")}
  }


  // Building block
  val createCheckResult = (freebies: List[Perk], isBlacklisted: Boolean) => (name: String) => {
    CheckResult(name, isBlacklisted, freebies)
  }

  def futureToFutureTry[T](f: Future[T]): Future[Try[T]] =    
    f.map(Success(_)).recover({case e => Failure(e)})

  def main(args: Array[String]): Unit = {

    val friends = List(
      Patron("Arnold", Some(Premium))
      , Patron("Jacob", Some(Standard))
      , Patron("Jerry", Some(Standard))
      // , Patron("Micah", None)
    )

    val futures: List[Future[CheckResult]] = friends
      .map(
        f => (checkMembership(f.membership.get), checkBlackList(f.name)).map2(
          // (freebies: List[Perk], isBlacklisted: Boolean) => {
          //   CheckResult(f.name, isBlacklisted, freebies)
          // }
          createCheckResult(_,_)(f.name)
        )
      )
    ;


    val liftedFutures = futures.map(futureToFutureTry(_))

    // List[Future[A]] to Future[List[A]]
    // val futureOfList = Future.sequence(futures)
    val futureOfListofSuccess = Future.sequence(liftedFutures).map(_.filter(_.isSuccess))
    val futureOfListofFailures = Future.sequence(liftedFutures).map(_.filter(_.isFailure))


    // Wait at the very end to resolve all futures
    val result1 = Await.result(futureOfListofSuccess, Duration(60, SECONDS))
    println(result1)
    val result2 = Await.result(futureOfListofFailures, Duration(60, SECONDS))
    println(result2)
  }
}
