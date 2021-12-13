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
        case class Staff(name: String, directReport: Option[String])


        val DirectReportPrism =  Prism[Staff, String](
            get = (s: Staff) => s.directReport,
            set = (s: Staff, d: String) => s.copy(directReport = Some(d))
        )

        val john = Staff("John", Some("Jack"))

        System.out.println(DirectReportPrism.get(john))
        
        // Yup it's composable
        // def composePrism[A,B,C](ab: Prism[A,B], bc: Prism[B,C]): Lens[A,C] = {
        //     return Lens[A,C](
        //         get = (a: A) => bc.get(ab.get(a)),
        //         set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
        //     )
        // }
    }
}
