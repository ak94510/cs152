object hw3 {
  def compose(first: (Integer) => Integer, second: (Integer) => Integer)(number: Integer) = {
    def step1 = second(number)

    first(step1)
  }

  def flip(input: (Int, Int) => Int)(number1: Integer, number2: Integer) = {
    input(number2, number1)
  }

  def zip(list1: List[Int], list2: List[Int], funct: (Int, Int) => Int): List[Int] = {
    if (list1.tail == Nil && list2.tail == Nil)
      funct(list1.head, list2.head) :: Nil
    else if (list1.tail == Nil)
      funct(list1.head, list2.head) :: list2.tail
    else if (list2.tail == Nil)
      funct(list1.head, list2.head) :: list1.tail
    else
      funct(list1.head, list2.head) :: zip(list1.tail, list2.tail, funct)
  }

  def combineNeighbors(list: List[Int], funct: (Int, Int) => Int): List[Any] = {
    if (list.tail == Nil)
      list.head :: Nil
    else {
      def shortList = list.tail

      funct(list.head, shortList.head) :: combineNeighbors(shortList.tail, funct)
    }
  }

  def iterateN(x: Int, funct: (Int) => Int, n: Int): List[Any] = {
    if (n == 1)
      x :: Nil
    else
      x :: iterateN(funct(x), funct, n - 1)
  }

  def iterateWhile(x: Int, funct: (Int) => Int, compare: (Int) => Boolean): List[Any] = {
    if (compare(x))
      x :: iterateWhile(funct(x), funct, compare)
    else
      Nil
  }

  def iterateUntil(x: Double, funct: Double => Double, compare: (Double, Double) => Boolean): List[Any] = {
    if (!compare(x, funct(x)))
      x :: iterateUntil(funct(x), funct, compare)
    else
      x :: Nil
  }
  def reduceWithDefault(default: Int, list: List[Int], funct: (Int, Int) => Int) = {
    if (list.isEmpty)
      default
    else {
      def adder(sum: Int, list: List[Int], funct: (Int, Int) => Int): Int = {
        if (list.isEmpty)
          sum
        else
          adder(funct(list.head, sum), list.tail, funct)
      }

      adder(default, list, funct)
    }
  }

  def flipList(list: List[Int]): List[Int] = {
    def flipHelper(memory: List[Int], rest: List[Int]): List[Int] = {
      if (rest.isEmpty)
        memory
      else
        flipHelper(rest.head :: memory, rest.tail)
    }

    flipHelper(Nil, list)
  }

  def otherReduceWithDefault(default: Int, list: List[Int], funct: (Int, Int) => Int) = {
    reduceWithDefault(default, flipList(list), funct)
  }

  def both(first: Int => Boolean, second: Int => Boolean)(x: Int): Boolean = {
    if (first(x) && second(x))
      true
    else
      false
  }

  def any(list: List[Int => Boolean])(x: Int): Boolean = {
    if (list.isEmpty)
      false
    else if (list.head(x))
      true
    else
      any(list.tail)(x)
  }
}