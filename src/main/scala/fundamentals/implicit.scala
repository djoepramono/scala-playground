package net.studikode.scala.fundamentals

object Implicit {

  // When you want to add additional function to an existing type, say int
  // It can be added via implicit like follows

  def main(args: Array[String]): Unit  = {
    // The name of the class here doesn't matter
    // But the parameter does matter
    implicit class AdditionalOpsOnInt(i: Int) {
      def isDivisibleBy3: Boolean = i % 3 == 0
    }

    // Basically the compiler here will try to find the "nearest" possible
    // function called isDivisibleBy3 and check if if the function signature
    // match, and if yes run it
    println(6.isDivisibleBy3.toString)
  }
}
