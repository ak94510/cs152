object problem1 extends App {
  def pairs(lst: List[Int]) : List[(Int, Int)] = {
    lst.sliding(2,2).foldLeft(List[(Int,Int)]()){(r,c) =>
      if(c.tail.isEmpty)
        r
      else
        r :+ (c.last, c.head) }
  }
  
  println(pairs(List(1, 2, 3, 4, 5, 6)))
  println(pairs(List(1, 2, 3, 4, 5)))
}
