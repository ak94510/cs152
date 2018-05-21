import org.scalatest.FunSuite

class MultiMapSuite extends FunSuite {
  class Person(val name : String) {
    override def toString = getClass.getName + "[name=" + name + "]"
  }

  class Student(name : String, val id : Int) extends Person(name) {
    override def toString = super.toString + "[id=" + id + "]"
  }

  test("Basic put and get") {
    val t1 = new MultiMap[String, Int]
    val t2 = t1.put("Harry", 42)
    val t3 = t2.put("Harry", 1729)
    assert(t3.get("Harry") == List(1729, 42))
  }

  test("Variance in values") {
    def wantsPersonMultiMap(t: MultiMap[String, Person]): List[String] = t.get("Harry").map(_.name)

    val t1 = new MultiMap[String, Student]
    val t2 = t1.put("Harry", new Student("Harry Lee", 42))
    assert(wantsPersonMultiMap(t2) == List("Harry Lee"))
  }

  test("Variance in keys") {
    def wantsStringMultiMap(t: MultiMap[String, Person]): List[String] = t.get("Harry").map(_.name)

    val t1 = new MultiMap[Any, Person]
    val t2 = t1.put("Harry", new Person("Harry Lee"))
    assert(wantsStringMultiMap(t2) == List("Harry Lee"))
  }
}
