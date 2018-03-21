〈!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"〉
〈html xmlns='http://www.w3.org/1999/xhtml'  
  〈head  
    〈meta content='text/html; charset=utf-8' http-equiv=content-type〉 
    〈title Green Sheet〉 
    〈link href='styles.css' rel='stylesheet' type='text/css'〉 
  〉
  〈body
    〈h1 San Jose State University | CS 152 Section 3 | Spring 2018〉
    〈h2 Midterm Exam〉
    〈h3 Exam Rules〉
    〈ul
      〈li You may put any files that you like on your laptop, including the slides, the Scala API, and your and my homework and lab solutions.〉
      〈li You may NOT use the Internet for anything during the exam other than accessing your Git repo. 〉
      〈li You may NOT communicate with anyone other than the exam proctor.〉
      〈li Immediately before the exam, run 〈 git pull〉 to get the starter file into your repo.〉
      〈li Do all your exam work in the folder called 〈 midterm〉 in your repo.〉
      〈li Put all your work into the files 〈 problem1.scala〉, 〈 problem2.scala〉, and so on, inside the 〈 src/main/scala〉 directory.〉
      〈li You 〈b must〉 run git commit every 10 minutes.〉
      〈li When the exam is over, run git push to push your repo.〉
      〈li The exam is 70 minutes long.〉
    〉
    〈h3 Exam Problems〉

    〈ol
      〈li Write a recursive Scala function 〈 pairs〉 that receives a 〈 List[Int]〉 and returns a 〈 List[(Int, Int)]〉, pairing adjacent elements. If the argument list has odd length, do not use the last element. For example,
        〈pre
pairs(List(1, 2, 3, 4, 5))
〉 returns
        〈pre
List((1, 2), (3, 4))
〉
        Submit a file 〈 problem1.scala〉
      〉
      〈li Repeat the preceding exercise, but now use a left fold. This is a bit tricky because the fold operation (which you need to design) only sees one new element at a time. Use as a seed value a tuple 〈 (〈var partial result list〉, 〈var previous value〉)〉, where the 〈var previous〉 value is an 〈 Option[Int]〉 that is either 〈 None〉 or 〈 Some(x)〉, where 〈 x〉 is the previous even value that you weren't yet able to pair up. (Or, if you don't want to use an 〈 Option〉, you can add a 〈 Boolean〉 value to the tuple.) Your operator should take in the next list value and either append a completed pair to the list, or update the previous value. Submit 〈 problem2.scala〉 with your function and a comment showing the fold diagram in ASCII art.〉
      〈li Given two lists of strings, produce a list of strings of all combinations of the first and second, separated by commas. Use 〈 map〉/〈 flatMap〉. For example,
        〈pre
allCombinations(List("Hello", "Goodbye"), List("World", "Pluto", "San José"))〉 yields
        〈pre
List("Hello,World", "Hello,Pluto", "Hello,San José", "Goodbye,World", "Goodbye,Pluto", "Goodbye,San José")
〉〉
      〈li Enhance the expression parser so that it can handle 〈 ^〉 as a “raise to a power” operator. Raising to a power binds more strongly than multiplication/division and is right-associative. For example, 4^2^3 = 4^(2^3) = 65536.〉
      〈li Enhance SL1 so that it can use the dot notation for 〈 head〉, 〈 tail〉, and 〈 isEmpty〉, instead of using functional notation as in homework 7. 〈br〉 We can no longer parse them as 〈 Funcall〉. Instead, parse them as
        〈pre
case class MethodCall(e: Expr, name: String) extends Expr 
〉 Make 〈 .〉 bind stronger than 〈 ::〉. To evaluate a 〈 MethodCall(e, name)〉:
        〈ul
          〈li evaluate 〈 e〉 and cast to 〈 List[Any]〉〉
          〈li lookup 〈 name〉 and cast to 〈 ListOp〉〉
          〈li Apply the function 〈 f〉 in the 〈 ListOp〉 to the value of 〈 e〉〉
        〉
 〉
    〉
  〉
〉
