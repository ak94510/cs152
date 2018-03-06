import java.util.Scanner

object hw5part1{
  abstract class Expr[T] {
    def value: T
  }

  class Op[T](function: Seq[T] => T, args: Expr[T]*) extends Expr[T] {
    def value = function.apply(args.map(_.value))
  }
  def sum(args: Int*): Int = args.reduce(_ + _)
  def product(args: Int*): Int = args.reduce(_ * _)

  case class Sum(arg: Expr[Int]*) extends Op[Int](sum, arg: _*)
  case class Product(arg: Expr[Int]*) extends Op[Int](product, arg: _*)

  case class Const[T](arg: T) extends Expr[T] {
    def value = arg
  }
  def Read: Const[Int] = {
    val scan = new Scanner(System.in)
    Const(scan.nextInt)
  }
  def Rand: Int = new scala.util.Random(42).nextInt()
}