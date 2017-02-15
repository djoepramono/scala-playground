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

abstract class Course(length: Int)
trait FastTrack {}
trait Remote {}
case class RegularCourse(length: Int, haveScholarship: Boolean)
  extends Course(length)
case class FastCourse(length: Int, haveScholarship: Boolean)
  extends Course(length)
  with FastTrack
case class SpecialCourse(length: Int, providesScholarship: Boolean)
  extends Course(length)
  with Remote
  with FastTrack
case class OnlineCourse(length: Int, gotScholarship: Boolean)
  extends Course(length)
  with Remote

object School {

  /**
   * A course is eligible for government funding if
   * - it is a fast track course or
   * - it is a regular course with that is 2 years long
   * - it is an online course with scholarship
   */
  def isItEligibleForGovernmentFunding(x:Course): String = x match {
    case s:FastTrack => "ft"
    /* Smart compiler warns about unreachable code on a commented line below*/
    // case FastCourse(_,_) => "wont"
    case RegularCourse(2,_) => "regular"
    case OnlineCourse(_,true) => "oc"
    case _ => "no"
  }

  def main(args: Array[String]): Unit = {

    // Note that there is no *new* in case class
    val IT = RegularCourse(4, true)
    val ShortIT = FastCourse(2,false)
    val RemoteIT = OnlineCourse(4, true)
    val Maths = RegularCourse(2, true)
    val SelfStudyMaths = SpecialCourse(4, true)
    val RemoteMaths = OnlineCourse(2, false)
    val SelfStudyPhysics = SpecialCourse(2, false)

    val courses = List(
      IT, ShortIT, RemoteIT, Maths, SelfStudyMaths, RemoteMaths,SelfStudyPhysics
    )
    val result = courses.map(x => isItEligibleForGovernmentFunding(x))
    println(result)
  }

}
