package playground

object Optics {
    def main(args: Array[String]): Unit = {
        
        case class Street ( number: Int, name: String );
        case class Address ( street: Street, city: String );
        val myAddress = Address(Street(123, "Test Road"), "Realtown")
        System.out.println(s"Hello my street name is ${myAddress.street.name}")

        // If we want to change the street name 
        val output1 = myAddress.copy(
            street = (myAddress.street.copy(
                name = "Copied Road")
            )
        )        
        System.out.println(s"Using .copy my street name can be changed to ${output1.street.name}")

        // Introducing Lens
        case class Lens[S, A] (
            get: S => A,
            set: (S, A) => S
        )
        
        // Given a street, get/set the name
        val streetNameLens = Lens[Street, String] (
            get = (s: Street) => s.name,
            set = (s: Street, n: String) => s.copy(name = n)
        )
        
        // Given an address, get/set a street
        val addressStreetLens = Lens[Address, Street] (
            get = (a: Address) => a.street,
            set = (a: Address, s: Street ) => a.copy(street = s)
        )

        // See how it works
        // get
        System.out.println(s"Using Lens.get my street name is ${streetNameLens.get(myAddress.street)}")
        // set
        System.out.println(s"Using Lens.set my street name can be changed to ${streetNameLens.set(myAddress.street, "New Test Road").name}")


        // Lens does compose
        def composeLens[A,B,C](ab: Lens[A,B], bc: Lens[B,C]): Lens[A,C] = {
            return Lens[A,C](
                get = (a: A) => bc.get(ab.get(a)),
                set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
            )
        }
        
        val output = composeLens(addressStreetLens, streetNameLens).get(myAddress)
        System.out.println(s"Using composed Lens my street name is $output");

    }
 }