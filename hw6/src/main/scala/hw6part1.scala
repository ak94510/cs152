object hw6part1 extends App {

  import java.io.InputStreamReader

  import scala.util.parsing.combinator._

  class Expr

  case class Number(value: Int) extends Expr

  case class Variable(name: String) extends Expr

  case class Operator(left: Expr, right: Expr, f: (Int, Int) => Int) extends Expr

  case class Def(name: String, expr: Expr)

  class SimpleLanguageParser extends JavaTokenParsers {
    def expr: Parser[Expr] = (term ~ rep(("+" | "-") ~ term)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "+" ~ y) => Operator(x, y, _ + _)
        case (x, "-" ~ y) => Operator(x, y, _ - _)
      }
    }

    def term: Parser[Expr] = (factor ~ rep(("*" | "/") ~ factor)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "*" ~ y) => Operator(x, y, _ * _)
        case (x, "/" ~ y) => Operator(x, y, _ / _)
      }
    }

    def prog: Parser[Prog] = rep(valdef) ~ expr ^^ {
      case s ~ e => Prog(s, e)
    }

    def factor: Parser[Expr] = ident ^^ (x => Variable(x)) | wholeNumber ^^ (x => Number(x.toInt)) | "(" ~> expr <~ ")"

    def valdef: Parser[Def] = ("val" ~> ident <~ "=") ~ expr <~ ";" ^^ { case s ~ e => Def(s, e) }

    case class Prog(defs: List[Def], expr: Expr) {
      def defmap: Map[String, Expr] = defs.map(t => t.name -> t.expr).toMap

      def eval: Int = {
        def evalHelper(symbols: Map[String, Expr], d: Expr): Int = {
          d match {
            case Number(num) => num
            case Variable(name) => evalHelper(symbols, symbols(name))
            case Operator(left, right, f) => f(evalHelper(symbols, left), evalHelper(symbols, right))
          }
        }

        evalHelper(defmap, expr)
      }
    }

  }
    val parser = new SimpleLanguageParser
    parser.parseAll(parser.prog, new InputStreamReader(System.in)) match {
      case parser.Success(result, next) => println(result.eval)
      case _ => println("Error")
    }
}