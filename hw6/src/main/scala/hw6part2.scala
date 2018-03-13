object hw6part2 extends App {

  import scala.util.parsing.combinator._
  import java.io.InputStreamReader

  class ListParser extends JavaTokenParsers {

    def repAlpha: Parser[List[String]] = "(" ~> rep1sep(wholeNumber, ",") <~ ")"

    def repBeta: Parser[List[String]] = rep1sep(wholeNumber, "::") ~ "::" ~ repAlpha ^^ { case numbers ~ _ ~ number => numbers ++ number }

    def list: Parser[List[String]] = repAlpha | repBeta

  }
    val parser = new ListParser
    parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
      case parser.Success(result, next) => println(result)
      case _ => println("Error")
    }

}
