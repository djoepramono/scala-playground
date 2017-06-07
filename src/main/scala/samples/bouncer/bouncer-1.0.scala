package net.studikode.scala.samples.bouncer

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._

// Bouncer after the first refactoring
object Bouncer1 {

  val ec = ExecutionContext.global

  // Functions representing expensive API calls
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
  }


  // Building block
  val createCheckResult = (freebies: List[Perk], isBlacklisted: Boolean) => (name: String) => {
    CheckResult(name, isBlacklisted, freebies)
  }


  def main(args: Array[String]): Unit = {

    val friends = List(
      Patron("Arnold", Some(Premium))
      , Patron("Jacob", Some(Standard))
      , Patron("Jerry", Some(Standard))
    )

    val futures: List[Future[CheckResult]] = friends
      .map(
        f => (checkMembership(f.membership.get), checkBlackList(f.name)).map2(
          createCheckResult(_,_)(f.name)
        )
      )
    ;

    // List[Future[A]] to Future[List[A]]
    val futureOfList = Future.sequence(futures)

    // Wait at the very end to resolve all futures
    val result = Await.result(futureOfList, Duration(60, SECONDS))
    println(result)
  }
}
