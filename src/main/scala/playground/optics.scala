package playground

object Optics {
    def main(args: Array[String]): Unit = {
        
        case class Street ( number: Int, name: String );
        case class Address ( street: Street, city: String );

        trait Lens[S, A] {
            def get(s: S): A
            def set(a: A, s:S): S
        }

        case object StreetNameLens extends Lens[Street, String] {
            def get(s: Street): String = s.name
            def set(n: String, s: Street) = s.copy(name = n)
        }
        
        case object AddressLens extends Lens[Address, Street] {
            def get(a: Address): Street = a.street
            def set(s: Street, a: Address) = a.copy(street = s)
        }

        // def overLens(get)

    }
 }