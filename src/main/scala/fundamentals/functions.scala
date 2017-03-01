package net.studikode.scala.fundamentals

object Map  {
  def main(args: Array[String]): Unit  = {

    val myList = List(1,2,3,4)
    val myList2 = List(0,2,4,6)

    /**
     * Mapping, performing a function on a data structure
     */
    val myMap = myList.map(v => addOne(v))
    println(myMap)

    /**
    * Flat map, map and well ..flatten them
    */
    val bigList = List(myList, myList2)
    val myFlatMap = bigList.flatMap(v => v)
    println(myFlatMap) // List(1, 2, 3, 4, 0, 2, 4, 6)

    // Things are a little bit complicated if you want apply a function first
    // val myFlatMap = bigList.map(v => addOne(v)) // error
    // val myFlatMap = bigList.map(v => v + 1) // error
    val myFlatMap2 = bigList.flatMap(v => {
      v.map(x => addOne(x))
    })
    println(myFlatMap2) // List(2, 3, 4, 5, 1, 3, 5, 7)
  }

  def addOne(x: Int): Int = {
    x + 1
  }
}
