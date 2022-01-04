package playground.optics

object Prism {
    def main(args: Array[String]): Unit = {

        // Prism is like Lens
        // From Monocle website: It is an optic used to select part of a Sum type
        
        case class Prism[S, A] (
            get: S => Option[A],
            set: (S, A) => S
        )

        // It's not straight forward
        // You need a sum type
        // Then you need to get the part of the subtype, which is a product type

        // Let's see some example
        // Staff and direct report
        // case class Staff(name: String, directReport: Option[String]) --> not really, there's no sum type
        
       
        // Vehicles
        // For example Plane and Car is a subtype of Vehicle
        // Car has a Driver
        // Plane on the other hand has Pilot
        
        // It's like getting a Driver from Vehicle, or getting a Pilot from Vehicle
        // We are looking at both combination of sum types and product types
        // Whilst Lens only worry about product types

        sealed trait Vehicle
        case class Car(driver: String) extends Vehicle        
        case class Plane(pilot: Pilot) extends Vehicle

        // Still cannot figure out the nested sum types
        // For example Sedan and Hatchback as a subtype of Car
        // So still can't figure out the correct implementation of Car, especially how to construct/create a copy
        // Let's skip that bit for now
        // Let's assume that our Prism category only deal with 1 layer of sum type morphism
    

        val driverPrism =  Prism[Vehicle, String](
            get = (v: Vehicle) => v match {
                case x: Plane => None
                case x: Car => Some(x.driver)
            },
            set = (v: Vehicle, d: String) => v match {
                case x: Plane => v
                case x: Car => x.copy(driver = d)
            }
        )

        val sedan = Car("Jake")
        System.out.println(s"The sedan driver is ${driverPrism.get(sedan).getOrElse("unknown")}")         
        

        // Over prism
        def overPrism[S,A](sa: Prism[S,A], f: A => A, s:S): S = {
            sa.get(s) match {
                case Some(value) => sa.set(s, f(value))
                case None => s
            }
        }

        val upperCaseDriver = (d: String) => d.toUpperCase()
        val y = overPrism(driverPrism, upperCaseDriver, sedan)
        System.out.println(s"The uppercased jeep driver is ${y}") // this return a Car(JAKE) but y is Vehicle not a Car
        
        // Let's use carPrism to make things better
        System.out.println(s"The uppercased jeep driver is ${driverPrism.get(y).getOrElse("unknown")}") // or you can use getOrElse just in case y is actually a Plane

        // Yup it's composable
        def composePrism[A,B,C](ab: Prism[A,B], bc: Prism[B,C]): Prism[A,C] = {
            return Prism[A,C](
                get = (a: A) => ab.get(a) match {
                    case None => None
                    case Some(value) => bc.get(value)
                },
                // set = (a: A, c: C) => ab.set(a, bc.set(ab.get(a), c))
                set = (a: A, c: C) => ab.get(a) match {
                    case None => a
                    case Some(value) => ab.set(a, bc.set(value, c))
                }
            )
        }

        // Do not have time to get an example of composed prism
        // But it would be something like getting Pilot's commerical licence, since Driver can be CommercialPilot or PrivatePilot        

        sealed trait Pilot
        case class CommercialPilot(name: String, commercialLicence: String) extends Pilot
        case class PrivatePilot(name: String) extends Pilot
    
        val commercialLicencePrism =  Prism[Pilot, String](
            get = (p: Pilot) => p match {
                case x: PrivatePilot => None
                case x: CommercialPilot => Some(x.commercialLicence)
            },
            set = (p: Pilot, l: String) => p match {
                case x: PrivatePilot => p
                case x: CommercialPilot => x.copy(commercialLicence = l)
            }
        )

        val pilotPrism =  Prism[Vehicle, Pilot](
            get = (v: Vehicle) => v match {
                case x: Car => None
                case x: Plane => Some(x.pilot)
            },
            set = (v: Vehicle, p: Pilot) => v match {
                case x: Car => v
                case x: Plane => x.copy(pilot = p)
            }
        )        

        val jet = Plane(CommercialPilot("Jess", "COM123"))
        val maybeCommercialLicence = composePrism(pilotPrism, commercialLicencePrism).get(jet)

        System.out.println(s"The jet pilot commercial licence is ${maybeCommercialLicence.getOrElse("unknown")}")
    }
}
