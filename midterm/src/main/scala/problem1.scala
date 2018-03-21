object problem1 extends App {
  def pairs(lst: List[Int]) : List[(Int, Int)] = {
    lst.grouped(2).Map(_).toList
  }
  
  println(pairs(List(1, 2, 3, 4, 5, 6)))
  println(pairs(List(1, 2, 3, 4, 5)))
}
