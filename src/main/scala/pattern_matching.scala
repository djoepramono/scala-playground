package net.studikode.patterns

object PatternMatching {
  def main(args: Array[String]) = {

    /**
     * Pattern Matching, similar to switch in other programming language
     * _ means "everything else" or the default
     */
    println("Pattern Matching")
    def intToString(x: Int): String = x match {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case _ => "no idea"
    }
    println(intToString(1))

    /**
     *
     */
    println("Pattern Matching part 2")
    def myFold(l: List[Int]): List[Int] = l match {
      case h :: t => {
        println(h)
        myFold(t)
      }
      case h => Nil
    }
    println(myFold(List(10,20,30)))
  }
}
