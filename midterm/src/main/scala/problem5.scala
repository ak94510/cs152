import java.io._
import scala.util.parsing.combinator._

class Definition
case class Valdef(name : String, expr : Expr) extends Definition
case class Defdef(name : String, expr : Expr) extends Definition

class Expr

case class Block(defs : List[Definition], expr : Expr)

case class Number(value : Int) extends Expr
case class Variable(name : String) extends Expr
case class Operator(left : Expr, right : Expr,
                    f: (Int, Int) => Int) extends Expr
case class Function(params : List[String], body : Block) extends Expr
case class IfExpr(cond : Expr, thenBlock : Block, elseBlock : Block) extends Expr
case class Funcall(fun : Expr, args : List[Expr]) extends Expr
case class Cons(a: Expr, b: Expr) extends Expr

case class Closure(params : List[String], body : Block, var env : List[(String, Any)]) {
  override def toString = "Closure(" + params + "," + body + ")"
}

case class MethodCall(e: Expr, name: String) extends Expr

case class ListOp(f: List[Any] => Any)

class SL1Parser extends JavaTokenParsers {
  def block: Parser[Block] = rep(valdef | defdef) ~ expr ^^ { case lst ~ e => Block(lst, e) }

  def expr: Parser[Expr] = ("if" ~> ("(" ~> expr <~ ")") ~ block <~ "else") ~ block ^^ {
    case e ~ b1 ~ b2 => IfExpr(e, b1, b2)
  } | expr2

  def expr2: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ {
    case a ~ lst => (a /: lst) {
      case (x, "+" ~ y) => Operator(x, y, _ + _)
      case (x, "-" ~ y) => Operator(x, y, _ - _)
    }
  }

  def term: Parser[Expr] = (term2 ~ rep(("*" | "/" ) ~ term2)) ^^ {
    case a ~ lst =>  (a /: lst) {
      case (x, "*" ~ y) => Operator(x, y, _ * _)
      case (x, "/" ~ y) => Operator(x, y, _ / _)
    }
  }

  def term2: Parser[Expr] = (factor ~ opt("::" ~> term2)) ^^ {
    case a ~ None => a
    case a ~ Some(b) => Cons(a, b)
  }

  def method: Parser[Expr] = (factor ~ opt("." ~> term2)) ^^ {
    case a ~ None => a
    case a ~ Some(b) => MethodCall(a,b)
  }


  def factor: Parser[Expr] = wholeNumber ^^ { x : String => Number(x.toInt) } |
    "(" ~> expr <~ ")" | valOrFuncall

  def valOrFuncall = valOrFun ~ rep( "(" ~> repsep(expr, ",") <~ ")" ) ^^ {
    case expr ~ Nil => expr
    case expr ~ args => (expr /: args)(Funcall(_, _))
    /*
                 . . .
                 /
             Funcall
             /     \
         Funcall   args(1)
         /     \
        expr   args(0)
     */
  }

  def valOrFun = "(" ~> expr <~ ")" |
    ident ^^ { Variable(_) } |
    funliteral

  def funliteral: Parser[Expr] = ("{" ~> repsep(ident, ",") <~ "=>") ~ block <~ "}" ^^ {
    case params ~ block => Function(params, block)
  }

  def valdef: Parser[Definition] = ("val" ~> ident <~ "=") ~ (expr <~ ";") ^^ {
    case name ~ expr => Valdef(name, expr)
  }

  def defdef: Parser[Definition] = ("def" ~> ident <~ "=") ~ (funliteral <~ ";") ^^ {
    case name ~ expr => Defdef(name, expr)
  }
}

object problem5 extends App {
  def spy[T](t : T) = { println(t); t }

  def lookup(name : String, symbols : List[(String, Any)]) =
    symbols.find(_._1 == name) match {
      case Some(pair) => pair._2
      case None => None
    }

  def eval(expr : Expr, symbols : List[(String, Any)]) : Any =
    expr match {
      case Number(num) => num
      case Variable(name) => lookup(name, symbols)
      case Operator(left, right, f) => {
        eval(left, symbols) match {
          case arg1 : Int =>
            eval(right, symbols) match {
              case arg2 : Int =>
                f(arg1, arg2)
            }
        }
      }

      case IfExpr(cond, block1, block2) => {
        eval(cond, symbols) match {
          case result : Int =>
            if (result > 0) evalBlock(block1, symbols) else evalBlock(block2, symbols)
        }
      }
      case MethodCall(express,string) => {
        eval(express,symbols) match {
          case result: List[Any] =>
            if(string.equals("head"))
              result.head
            else if(string.equals("tail"))
              result.tail
            else if(string.equals("isEmpty"))
              result.isEmpty
        }
      }

      case Funcall(fun, args) => eval(fun, symbols) match {
        case Closure(params, body, syms) =>
          evalBlock(body, params.zip(args.map(eval(_, symbols))) ++ syms)
      }

      case Function(params, body) => Closure(params, body, symbols)

      case Cons(a, b) => eval(a, symbols) :: eval(b, symbols).asInstanceOf[List[Any]]

      case _ => expr
    }

  def evalDef(symbols : List[(String, Any)], defn : Definition) =
    defn match {
      case Defdef(name, Function(params, body)) => {
        val cl = Closure(params, body, symbols)
        val syms = (name, cl) :: symbols
        cl.env = syms
        syms
      }
      case Valdef(name, Function(params, body)) => { (name, Closure(params, body, symbols)) :: symbols }
      case Valdef(name, expr) => { (name, eval(expr, symbols)) :: symbols }
    }

  def evalBlock(block : Block, symbols : List[(String, Any)]) : Any =
    eval(block.expr, (symbols /: block.defs) { evalDef(_, _) } )

  val head = ListOp(_.head)
  val tail = ListOp(_.tail)
  val isEmpty = ListOp(l => if (l.isEmpty) 1 else 0)
  val initialSymbols = List(("Nil", Nil), ("head", head), ("tail", tail), ("isEmpty", isEmpty))

  val parser = new SL1Parser
  val result = parser.parseAll(parser.block, "(1 :: Nil).head")
  println(evalBlock(result.get, initialSymbols))
  val result2 = parser.parseAll(parser.block, "(1 :: 2 :: Nil).tail.head")
  println(evalBlock(result2.get, initialSymbols))
}

