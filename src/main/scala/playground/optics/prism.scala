package playground.optics

object Prism {
    def main(args: Array[String]): Unit = {

        // Prism is like Lens
        // Except that it works on optional type*
        // For simplicity, we would just hardcode Option i.e. Option is not in the type param
        case class Prism[S, A] (
            get: S => Option[A],
            set: (S, A) => S
        )

        // Staff and direct report
        // This is wrong
        // case class Staff(name: String, directReport: Option[String])

        // It's all about sum types not product types
        sealed trait Vehicle
        case class Car(driver: String) extends Vehicle
        case class Plane(pilot: String) extends Vehicle


        val ControllerPrism =  Prism[Vehicle, String](
            get = (v: Vehicle) => v match {
                case Plane(pilot) => Some(pilot)
                case Car(driver) => Some(driver)
            },
            set = (v: Vehicle, s: String) => v match {
                case x: Plane => x.copy(pilot = s)
                case x: Car => x.copy(driver = s)
            }
        )

        val jeep = Car("Jake")
        val jet = Plane("Jess")

        System.out.println(ControllerPrism.get(jeep))
        
        // Yup it's composable
        // def composePrism[A,B,C](ab: Prism[A,B], bc: Prism[B,C]): Prism[A,C] = {
        //     return Prism[A,C](
        //         get = (a: A) => bc.get(ab.get(a)),
        //         set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
        //     )
        // }
    }
}
