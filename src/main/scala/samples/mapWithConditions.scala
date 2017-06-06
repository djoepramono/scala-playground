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
case object NoPerk extends Perk

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
    Future {(n == "Jerry")}
    // Future {throw new Exception("Rawr")}
  }


  // Building block
  val createCheckResult = (freebies: List[Perk], isBlacklisted: Boolean) => (name: String) => {
    CheckResult(name, isBlacklisted, freebies)
  }

  def futureToFutureTry[T](f: Future[T]): Future[Try[T]] =
    f.map(Success(_)).recover({case e => Failure(e)})

  /**
   * `pure` should take a value of any type and return an applicative value with that value inside it.
   * A better way of thinking about pure would be to say that it takes a value and puts it in some sort of default (or pure) context
   */
  // def succeedsIfNone[F[_] : Functor, A, B](verifier: A => F[FailsWithVerificationErrors[B]])
  //                                         (input: Option[A]):
  //   F[FailsWithVerificationErrors[Option[B]]] = {
  //   input match {
  //     case None => Right(None).pure
  //     case Some(x) => verifier(x).map(_.map(v => Some(v)))
  //   }
  // }

 /**
  * Lift checkMembership to accept Option[Membership]
  * Think it like A = membership, B = perk
  */
 def checkMaybeMembership[A,B](f: A => Future[List[B]])
                              (o: Option[A])
 : Future[List[B]] = {
   o match {
     case Some(x) => f(x)
     case None => Future {List[B]()}
   }
 }



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
