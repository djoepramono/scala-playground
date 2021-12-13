package playground.optics

object Prism {
    def main(args: Array[String]): Unit = {

        // Prism is like Lens
        case class Prism[S, A] (
            get: S => Option[A],
            set: (S, A) => S
        )

        // First letter of string
        case class Staff(name: String, directReport: Option[Staff])


        val DirectReportPrism =  Prism[Staff, Staff](
            get = (s: Staff) => Some(s.charAt(0)),
            set = (s: String, n: String) => s.replace(s, n)
        )

        System.out.println(FirstPrism.get("John Doe"))


    }
}
