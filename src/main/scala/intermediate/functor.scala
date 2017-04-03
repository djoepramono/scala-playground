package net.studikode.scala.intermediate

/*
 * The concept of functor is not built in to Scala however, thus you need either
 *   `scalaz` or `cats`
 * Cats is currently considered 'better'
 * For whatever reason you need both import ...
 */
// import scalaz._
// import Scalaz._
import cats._
import cats.implicits._

/**
 * Data structure (e.g. List) consists of
 *   - type (e.g. Int)
 *   - categories/context (e.g. the list itself)
 * A functor is a data structure that can be mapped so that the type changed
 *   while maintaining the categories (e.g. List can be mapped from Int to String)
 * In other words it's
 *   F[A] => F[B]
 */
object Functor {

  /**
   * This function accept a functor (e.g. List or Option)
   *   and transform its type and value
   */
  def addOneToString[F[_]: Functor](something: F[Int]):F[String] = {
    something.map {
      x => (x + 1).toString
    }
  }

  def main(args: Array[String]): Unit = {
    println(addOneToString(List(1)))
    println(addOneToString(Option(1)))
    // println(addOneToString(Right(1)))  // Apparently Either is not a functor
  }


}
