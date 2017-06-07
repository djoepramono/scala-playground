package net.studikode.scala.samples.bouncer

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
