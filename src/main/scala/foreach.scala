sealed trait Animal
sealed trait Dog extends Animal
case object Pug extends Dog
case object Bulldog extends Dog

object ForEach {
  def main(args: Array[String]) = {

    val dogs = List(Pug, Bulldog)
    dogs.foreach {
      case x: Bulldog.type => println(s"The bulldog is: ${x}");
      case x: Animal => println(s"The animal is: ${x}");
    }
    println("hey");
  }
}
