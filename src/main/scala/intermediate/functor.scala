package net.studikode.scala.intermediate

import scalaz._

/**
 * Data structure (e.g. List) contains type (e.g. Int)
 *   and categories/context (e.g. the list itself)
 * A functor is a data structure that can be mapped so that the type changed
 *   while maintaining the categories (e.g. List can be mapped from Int to String)
 * In other words it's
 * F[A] => F[B]
 * The concept of functor is not built in to Scala however
 */
object Functor {
  def main(args: List[String]) = {
    println("kaboom")
    kaboom(List(1))
  }

  def kaboom[F[_]: Functor](something: F[Int]):F[String] = {
    something.map {
      x => (x + 1).toString
    }
  }
}
