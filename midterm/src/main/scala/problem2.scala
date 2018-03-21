object problem2 extends App {
  def pairs(lst: List[Int]) = {
    val seed: (List[(Int, Int)], Option[Int]) = (Nil, None)
    val foldResult = lst.foldLeft(seed)((x,y) => {
      if(x._2.isDefined){
        ((x._2.get, y) :: x._1, None)
      }
      else
        (x._1,Some(y))
    })
    foldResult._1.reverse
  }

  /*
      Your fold diagram here
   */

  println(pairs(List(1, 2, 3, 4, 5, 6)))
  println(pairs(List(1, 2, 3, 4, 5)))
}
