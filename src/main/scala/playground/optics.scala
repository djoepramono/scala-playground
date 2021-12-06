package playground

object Optics {
    def main(args: Array[String]): Unit = {
        
        case class Street ( number: Int, name: String );
        case class Address ( street: Street, city: String );

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
        val AddressStreetLens = Lens[Address, Street] {
            get = (a: Address) => a.street,
            set = (a: Address, s: Street ) => a.copy(street = s)
        }

        // Lens does compose
        def composeLens[A,B,C](ab: Lens[A,B], bc: Lens[B,C]): Lens[A,C] = {
            return Len[A,C](
                get = bc.get(ab.get(_)),
                set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
            )
        }

    }
 }