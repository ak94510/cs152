object problem2 extends App {
  def pairs(lst: List[Int]) = {
    val seed: (List[(Int, Int)], Option[Int]) = (Nil, None)
    val foldResult = ...
    foldResult._1
  }
  
  /*
      Your fold diagram here
   */
    
  println(pairs(List(1, 2, 3, 4, 5, 6)))
  println(pairs(List(1, 2, 3, 4, 5)))  
}
