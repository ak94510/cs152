object hw6part3 extends App {

  import scala.util.parsing.combinator._
  import java.io.InputStreamReader

  class ListParser extends JavaTokenParsers {


    case class Element(name: String, attrs: List[(String, String)], children: List[Element])

    def attr: Parser[(String, String)] = ident ~ "=" ~ stringLiteral ^^ { case first ~ "=" ~ next => (first, next) }

    def list: Parser[Element] = (("<" ~ ident ~ rep(attr) ~ ">" ~ rep(list) ~ "</" ~ ident ~ ">") | ("<" ~ ident ~ rep(attr) ~ "/>")) ^? {
      case "<" ~ i ~ a ~ ">" ~ c ~"</" ~ j ~ ">" if i == j => Element(i.toString, a.asInstanceOf[List[(String,String)]], c.asInstanceOf[List[Element]])
      case "<" ~ i ~ a ~ "/>" => Element(i.toString(), a.asInstanceOf[List[(String,String)]],List())
    }
  }
    val parser = new ListParser
    parser.parseAll(parser.list, new InputStreamReader(System.in)) match {
      case parser.Success(result, next) => println(result)
      case _ => println("Error")
    }
}
