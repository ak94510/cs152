object hw5part2{
  abstract class Expr[T] {
    def eval(symbols: Map[String, T]): T
  }
  case class Def[T](name: String,expr: Expr[T])

  class Op[T](function: Seq[T] => T, args: Expr[T]*) extends Expr[T] {
    override def eval(symbols: Map[String, T]): T = function(args.map(_.eval(symbols)))
  }
  def sum(args: Int*): Int = args.reduce(_ + _)
  def product(args: Int*): Int = args.reduce(_ * _)

  case class Sum(arg: Expr[Int]*) extends Op[Int](sum, arg: _*)
  case class Product(arg: Expr[Int]*) extends Op[Int](product, arg: _*)

  case class Const[T](arg: T) extends Expr[T] {
    override def eval(symbols: Map[String, T]): T = arg
  }

  case class Var[T](name: String) extends Expr[T]{
    override def eval(symbols: Map[String, T]): T = symbols(name)
  }
}