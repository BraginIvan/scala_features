package macroses

import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */

class ParseCaseClassSpec extends WordSpecLike{

  "macros" must{

    case class testCaseClass(a: Int, b: String, c: String){
      def values = ParseCaseClass.st[this.type]
    }

    "parse parametrs" in{

      val generetedList  = testCaseClass(1, "str", "str2").values
      val checkingList = List(("a " ,1), ("b " ,"str"), ("c " ,"str2")) // TODO: why spaces?
      
      assert(generetedList == checkingList)

    }


  }

}
