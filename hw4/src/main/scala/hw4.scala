object hw4 {
  import scala.util.matching.Regex

  def firstMatch(input: String, patterns: List[Regex]): (String, Integer) = {
    var index = input.length
    var string = ""
    for (i <- 0 to patterns.length - 1) {
      if (patterns(i).findPrefixOf(input).getOrElse(null) != null) {
        val potential = input.indexOf(patterns(i).findPrefixOf(input).getOrElse(null))
        if (potential < index) {
          index = potential
          string = patterns(i).findPrefixOf(input).getOrElse(null)
        }
      }
    }
    if (string == "")
      null
    else
      (string, index)
  }

  def flipList(list: List[String]): List[String] = {
    def flipHelper(memory: List[String], rest: List[String]): List[String] = {
      if (rest.isEmpty)
        memory
      else
        flipHelper(rest.head :: memory, rest.tail)
    }

    flipHelper(Nil, list)
  }

  def tokens(input: String, patterns: List[Regex], omit: List[Regex]): (List[String], Integer) = {
    def tokenHelper(temp: String, list: List[String]): (List[String], Integer) = {
      if (temp.isEmpty)
        (list, -1)
      else {
        val patternsMatch = firstMatch(temp, patterns)
        val omitMatch = firstMatch(temp, omit)
        if (patternsMatch == null && omitMatch == null)
          (list, input.indexOf(temp))
        else if (patternsMatch == null && omitMatch != null) {
          if (omitMatch._2 == 0)
            tokenHelper(temp.substring(omitMatch._1.length), list)
          else
            (list, input.indexOf(temp))
        }
        else if (patternsMatch._2 == 0)
          tokenHelper(temp.substring(patternsMatch._1.length), patternsMatch._1 :: list)
        else
          (list, input.indexOf(temp))
      }
    }

    val temp = tokenHelper(input, List())
    (flipList(temp._1), temp._2)
  }

  val characters = (s: String) => s.toList.map("" + _)
  val letters = Map("2" -> "ABC", "3" -> "DEF", "4" -> "GHI", "5" -> "JKL", "6" -> "MNO", "7" -> "PRS", "8" -> "TUV", "9" -> "WXY").map(e => (e._1, characters(e._2)))
  val cats = (s: List[String], t: List[String]) => t.flatMap(y => s.map(x => x + y))
  val words = io.Source.fromURL("http://horstmann.com/sjsu/spring2018/cs152/words").
    getLines.filter(w => Character.isLowerCase(w(0)) && w.length > 1).
    map(_.toUpperCase).toSet + "SCALA"
  val wordsForDigits = (digits: String) => {
    val list = characters(digits)
    list.map(y => letters(y)).reduceLeft(cats(_, _)).filter(p => words.contains(p))
  }
  val catsSpaces = (s: List[String], t: List[String]) => t.flatMap(y => s.map(x => x + " " + y))
  val wordsForDigitsSequence = (seq: List[String]) => seq.map(e => wordsForDigits(e)).reduceLeft(catsSpaces)
  val grow1 = (c: String, substringLists: List[List[String]]) => substringLists.map(x => c :: x)
  val grow2 = (c: String, substringLists: List[List[String]]) => substringLists.map(x => c + x.head :: x.tail)
  val grow = (c: String, a: List[List[String]]) => a.flatMap(x => grow1(c, a) ++ grow2(c, a)).distinct
  val substrings = (s: String) => characters(s.substring(0, s.length - 1)).foldRight(List(List[String](s.substring(s.length - 1))))((b, a) => grow(b, a))
  val phoneMnemonics = (digits: String) => substrings(digits).flatMap(wordsForDigitsSequence)
}
