import scala.util.parsing.combinator._
object hw5part3 {

  class SimpleLanguageParser extends JavaTokenParsers {
    def Expr: Parser[Any] = Term ~ opt(("+" | "-") ~ Expr)

    def Term: Parser[Any] = Factored ~ opt(("*" | "/") ~ Term)

    def Factored: Parser[Any] = wholeNumber | ident | "(" ~ Expr ~ ")"

    def Eval(x: Any, symbols: Map[String, Int]): Int = x match {
      case a ~ Some("+" ~ b) => Eval(a, symbols) + Eval(b, symbols)
      case a ~ Some("-" ~ b) => Eval(a, symbols) - Eval(b, symbols)
      case a ~ Some("*" ~ b) => Eval(a, symbols) * Eval(b, symbols)
      case a ~ Some("/" ~ b) => Eval(a, symbols) / Eval(b, symbols)
      case a ~ None => Eval(a, symbols)
      case a: String => try {
        Integer.parseInt(a)
      } catch {
        case e: NumberFormatException => {
          symbols(a)
        }
      }
      case "(" ~ a ~ ")" => Eval(a, symbols)
    }
  }

}
