package playground

object adt {


    /**
      * Product Type
      * combining two types with an AND
      * a `Staff` consists of `name` and `age`
      */
    case class Staff(name: String, age: Int)


    /**
      * Sum Type
      * combining two types with an OR
      * a `Team` can only be `Finance` or `Tech`
      */
    sealed trait Team
    case object Finance extends Team
    case object Tech extends Team

    /**
      * Pattern Matching
      * test an expression for a certain pattern
      * searching in `team` is Finance or Tech
      */

    def checkTeam(team: Team):String = team match {
        case Finance => "The team is finance"
        case Tech => "The team is tech"
    }
}