package net.studikode.scala.intermediate

object Eta {
  def main(args: Array[String]) = {
    println(greet(GreetingHelper.frenchGreeting,"Arnaud"))
    println(greet(GreetingHelper.englishGreeting,"Jake"))
    println(greet(GreetingHelper.chineseGreeting,"Michael"))
    println(greet(GreetingHelper.indonesianGreeting("pagi"),"Joe"))

    // Manual eta
    println(greet(GreetingHelper.f1("malam"), "Joe2"))
    println(greet(GreetingHelper.f2("sore"), "Joe3"))
  }

  // A function with vague dependency injection
  //   e.g. a function that returns an int
  def greet(greeting: String, person: String) = {
    s"${greeting}, ${person}"
  }
}

object GreetingHelper {

  // This three different ways of producing string can be injected into `greet`
  def frenchGreeting = "Bonjour"                        // method without ()
  val englishGreeting = "Good morning"                  // object's property
  def indonesianGreeting(a: String) = "Selamat " + a    // method with () and parameter
  def chineseGreeting() = "Zao An"                      // method with ()

  // Methods that is injected, behind the scenes is lifted into a function implicity
  // Because method is not a function
  // Remember though that val can be a function

  val f1 = indonesianGreeting _// Lifting method frenchGreeting to a string f1
  val f2: (String) => String = indonesianGreeting // Lifting method chineseGreeting to a function f2
}
