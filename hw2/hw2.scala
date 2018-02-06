object hw2 {
  def subs(string: String): String = {
    def subHelper(index: Int,split: String): String = {
      if (index ==string.length()) split
      else
        subHelper(index + 1,split) + "|" + subHelper(index+1,split + string.charAt(index))
    }
    subHelper(0,"")
  }
  def lcs(a:String,b:String): String = {
    def lengthCounter(aString: String, bString: String, holder: String): String = {
      if (aString.charAt(0).equals(bString.charAt(0)))
      {
        if(aString.length==1 || bString.length()==1)
          holder + aString.charAt(0)
        else
          lengthCounter(aString.substring(1), bString.substring(1), holder + aString.charAt(0) )
      }
      else
        holder
    }
    def stringIndex(theRestOfA: String,theRestOfB:String, longest: String): String = {
      if (theRestOfA.length() == 1)
        longest
      else {
        def competitor: String = lengthCounter(theRestOfA,theRestOfB,"")
        if(competitor.length>longest.length) {
          if (theRestOfB.length == 1)
            stringIndex(theRestOfA.substring(1), b, competitor)
          else
            stringIndex(theRestOfA, theRestOfB.substring(1), competitor)
        }
        else{
          if (theRestOfB.length == 1)
            stringIndex(theRestOfA.substring(1), b, longest)
          else
            stringIndex(theRestOfA, theRestOfB.substring(1), longest)
        }
      }
    }
    stringIndex(a,b,"")
  }
  def onebits(n: Int) : List[Int] = {
    def poweroftwo(p: Int, counter: Int): List[Int] = {
      if (p == 0)
        Nil
      else {
        if (p % 2 == 1) {
          counter :: poweroftwo(p / 2,counter+1)
        }
        else {
          poweroftwo(p/ 2,counter+1)
        }
      }
    }
    poweroftwo(n,0)
  }
}
