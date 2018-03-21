object problem3 extends App {
   def allCombinations(a: List[String], b: List[String]) = {
      a.flatMap(x => b.map(y => x + "," +y))
   }
     
     
   println(allCombinations(List("Hello", "Goodbye"), List("World", "Pluto", "San José")))     
}
