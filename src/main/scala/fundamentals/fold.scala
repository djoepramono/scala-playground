package net.studikode.scala.fundamentals

object Fold{
  def main(args: Array[String]) = {

    val myList = List(1,2,3,4,5)

    /**
     * This basically says
     * start with an integer 0
     * add 0 with the left most element in the list (1)
     *  then add the result with the next element on the right (2)
     *  and so on ...
     * continue this until the last integer
     */
    val result = myList.foldLeft(0) {
      (x,y) => {
        println(s"${x} + ${y} is ${x+y}")
        x + y
      }
    }

    println(s"So the result of left-folding ${myList} is ${result}")

    /**
     * `fold` and `foldLeft` are basically almost the same thing ...
     * almost, because there's no guarantee that `fold` starts from the left
     * it starts from the order of the elements is added
     */
    val myLongerList =  5 :: 6 :: myList
    val result2 = myLongerList.fold(0) {
      (x,y) => {
        println(s"${x} + ${y} is ${x+y}")
        x + y
      }
    }

    println(s"So the result of just-folding ${myLongerList} is ${result2}")

    /**
     * Things go a little bit differently with `foldRight`
     * The end result is still the same but the sequence changes
     */
    val result3 = myList.foldRight(0) {
      (x,y) => {
        println(s"${x} + ${y} is ${x+y}")
        x + y
      }
    }

    println(s"So the result of right-folding ${myList} is ${result3}")
  }
}
