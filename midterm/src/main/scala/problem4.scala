import java.io._
import scala.util.parsing.combinator._

object problem4 extends App {
  def intpow(a: Int, b: Int) = math.pow(a, b).asInstanceOf[Int]

  class SimpleLanguageParser extends JavaTokenParsers {
    def expr: Parser[Int] = (term ~ rep(("+" | "-") ~ expr)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "+" ~ y) => x + y
        case (x, "-" ~ y) => x - y
      }
    }

    def term: Parser[Int] = (pow ~ rep(("*" | "/") ~ term)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "*" ~ y) => x * y
        case (x, "/" ~ y) => x / y
      }
    }

    def pow: Parser[Int] = (factor ~ rep(("^") ~ pow)) ^^ {
      case a ~ lst => (a /: lst) {
        case (x, "^" ~ y) => intpow(x, y)
      }
    }

    def factor: Parser[Int] = wholeNumber ^^ (_.toInt) | "(" ~> expr <~ ")"
  }

  val parser = new SimpleLanguageParser
  val result = parser.parseAll(parser.expr, "4 ^ 2 ^ 3")
  println(result)
  val result2 = parser.parseAll(parser.expr, "1 + 4 * 2 ^ 3")
  println(result2)
}

