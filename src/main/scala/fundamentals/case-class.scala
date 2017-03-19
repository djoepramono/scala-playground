package net.studikode.scala.fundamentals

abstract class Fruit
trait Local
trait Seedless
// Multiple inheritance
// Or really it is more like Composition rather than inheritance
case class Banana() extends Fruit with Local with Seedless
case class Apple() extends Fruit with Local with Seedless

object CaseClasses {
  def main(args: Array[String]): Unit = {
    // Different object same class
    val PinkLady = Apple()
    val Fuji = Apple()
    println(PinkLady == Fuji)                   // true

    // Can check parents' class
    val LadyFinger = Banana()
    println(LadyFinger.isInstanceOf[Fruit])     // true
    println(LadyFinger.isInstanceOf[Seedless])  // true
  }
}
