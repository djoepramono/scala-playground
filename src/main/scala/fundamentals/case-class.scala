package studikode.scala.fundamentals

/**
 * Case class is pretty much the same as regular classes, except
 * - immutable
 * - can be pattern matched
 */
abstract class Course(manager: String)
case class RegularCourse(length: Int, caretaker: String) extends Course(caretaker)
// case class WorkReady(length: Int) extends Course
// case class FastTrack(length: Int) extends Course

object School {
  def main(args: Array[String]) = {
      var IT = new RegularCourse(2,"John Doe")
      println(IT)
  }
}
