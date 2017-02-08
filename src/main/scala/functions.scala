package net.studikode.function

object HigherOrderFunction  {
  def main(args: Array[String]): Unit  = {

    val myList = List(1,2,3,4)

    /**
     * Mapping, performing a function on a data structure
     */
    println("Mapping")
    var myMap = myList.map(v => addOne(v))
    println(myMap)

    /**
     * Folding, pretty much performing a function
     * The first parameter is pretty much a initial value
     */
    println("Folding")
    var myFold = myList.fold(0)(addTheseTwo(_,_))
    println(myFold)
  }

  def addOne(x: Int) = {
    x + 1
  }

  def addTheseTwo(a: Int, b: Int): Int = {
    a + b
  }
}
