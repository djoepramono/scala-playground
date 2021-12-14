package playground.optics

object Prism {
    def main(args: Array[String]): Unit = {

        // Prism is like Lens
        // Except that it works on optional type*
        // For simplicity, we would just hardcode Option i.e. Option is not in the type param
        // A is a subtype of S
        // or is this Optional? i.e. another optic. not prism. Nope it is not because the signature of `set`
        case class Prism[S, A] (
            get: S => Option[A],
            set: (S, A) => S
        )

        // Staff and direct report
        // This is wrong
        // case class Staff(name: String, directReport: Option[String])

        // It's all about sum types not product types
        // Actually no this is also wrong
        // is it? maybe not
        sealed trait Vehicle
        case class Car(driver: String) extends Vehicle
        case class Plane(pilot: String) extends Vehicle

        // It should be
        // note that the struct is similar between Car and Plane
        // nah nvm this is wrong
        // sealed trait Vehicle
        // case class Car(controller: String) extends Vehicle
        // case class Plane(controller: String) extends Vehicle


        val carPrism =  Prism[Vehicle, Car](
            get = (v: Vehicle) => v match {
                case x: Plane => None
                case x: Car => Some(x)
            },
            set = (v: Vehicle, s: Car) => v match {
                case x: Plane => v
                case x: Car => s
            }
        )

        val sedan = Car("Jake")
        val jet = Plane("Jess")

        System.out.println(s"The jeep driver is ${(carPrism.get(sedan).getOrElse(Car("unknown"))).driver}") 
        System.out.println(s"The jet driver is ${(carPrism.get(jet).getOrElse(Car("unknown"))).driver}")
        

        // Over prism
        def overPrism[S,A](sa: Prism[S,A], f: A => A, s:S): S = {
            sa.get(s) match {
                case Some(value) => sa.set(s, f(value))
                case None => s
            }
        }

        val upperCaseDriver = (c: Car) => c.copy(driver = c.driver.toUpperCase())
        val y = overPrism(carPrism, upperCaseDriver, sedan)
        System.out.println(s"The uppercased jeep driver is ${y}") // this return a Car(JAKE) but y is Vehicle
        
        // Let's use carPrism to make things better
        System.out.println(s"The uppercased jeep driver is ${carPrism.get(y).get.driver}") // or you can use getOrElse just in case y is actually a Plane

        // Yup it's composable
        // def composePrism[A,B,C](ab: Prism[A,B], bc: Prism[B,C]): Prism[A,C] = {
        //     return Prism[A,C](
        //         get = (a: A) => bc.get(ab.get(a)),
        //         set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
        //     )
        // }
    }
}
