package net.studikode.scala.fundamentals

import cats._
import cats.implicits._

object FoldMap {
  def main(args: Array[String]) = {
    val l = List(1,2,3,4)

    // Map first then fold
    val l2 = l.foldMap(x => x + 1)

    println(l2)
  }
}
