package net.studikode.scala.intermediate

// bval Student

sealed trait Data {	
	type E 
	val value: E
	def show: E => String
}

case class PersonData[T] (
	value: T,
	show: T => String
) extends Data {type E = T}

case class Student (name: String)

object Existential {
  def main(args: Array[String]) = {
  	println("Welcome to existential")

  	// val me = PersonData[Student](Student("John"), (a: Student) => a.name)

  	// me.show
  }

  def foo(x: PersonData[T] forSome {type T}) = println(x.show)
}

object Exist {
	def main(args: Array[String]): Unit = {
		println("hullo")
		foo(Array("a", "b", "v"))
	}

	def foo(x : Array[T] forSome { type T}) = println(x.length)
}