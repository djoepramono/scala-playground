package net.studikode.scala.fundamentals

/**
 * Case class is pretty much the same as regular classes, except
 * - immutable
 * - can be pattern matched
 * When not to use? When it contains complex behaviour or stateful computations
 */

/**
 * abstract class or trait?
 * abstract classes have constructor and type parameters
 * a class can inherit from one abstract classes but multiple trait
 */

abstract class Course(manager: String)
case class RegularCourse(length: Int, caretaker: String) extends Course(caretaker)
case class WorkReady(length: Int, caretaker: String) extends Course(caretaker)
// case class FastTrack(length: Int) extends Course

object School {
  def main(args: Array[String]) = {

    // Note that there is no *new* in case class
    var IT = RegularCourse(2,"John Doe")
    var Geography = WorkReady(4, "Mary Jane")

    println(IT)
  }
}
