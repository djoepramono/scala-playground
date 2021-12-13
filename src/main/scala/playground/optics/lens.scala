package playground.optics

object Lens {
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


        // Modify
        def overLens[S, A](sa: Lens[S,A], f: A => A, s: S): S = {
            return sa.set(s, f(sa.get(s)))
        }

        val output3 = overLens(streetNameLens, (a: String) => a.toUpperCase(), myAddress.street)
        System.out.println(s"A lens can be composed with a function to modify lens value, e.g. my address is now ${output3.name}");

        // Lens Laws
        // Identity, get a value and set it back to the object should give you identical object
        def getSet[S, A](lens: Lens[S, A], s: S): Boolean =
            lens.set(s, lens.get(s)) == s

        val lawOutput1 = getSet[Street, String](streetNameLens, myAddress.street)
        System.out.println(s"Identity law, if I get a value and set the value back to the object, do I get identical object? $lawOutput1")

        // Retention, if you set a value and then get it. You get the value back
        def setGet[S, A](lens: Lens[S, A], s: S, a: A): Boolean =
            lens.get(lens.set(s, a)) == a

        val lawOutput2 = setGet[Street, String](streetNameLens, myAddress.street, "Testing Street")
        System.out.println(s"Retention law, if I set a value and get it, do I get the value back?  $lawOutput2")

        // Double set, if you set twice and get the value, you get the latest value
        def putPut[S, A](lens: Lens[S, A], s: S, a: A, b: A): Boolean =
            lens.get(lens.set(lens.set(s, a), b)) == b
        val lawOutput3 = putPut[Street, String](streetNameLens, myAddress.street, "Testing Street", "New Testing Street")
        System.out.println(s"Double set law, if I set value twice, will I get the latest value back? $lawOutput3")
    }
 }
