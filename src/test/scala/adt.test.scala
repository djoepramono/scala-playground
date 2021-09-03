package playground

import org.scalatest.funspec.AnyFunSpec
import org.scalactic.TypeCheckedTripleEquals
import playground.adt._


class AdtTest extends AnyFunSpec with TypeCheckedTripleEquals{
    describe("checkTeam") {
        it ("should return the correct team") {          
            assert(checkTeam(Finance) == "The team is finance")  
            assert(checkTeam(Tech) == "The team is tech")
        }        
    }
}