package features.cassandra

import com.datastax.driver.core.querybuilder.QueryBuilder
import macroses.ParseCaseClass
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */
class CaseClassToInsertSpec extends WordSpecLike{

  case class testCaseClass(a: Int, b: String, c: String) extends CaseClassToInsert{
    def statement = prepareStatement("name", ParseCaseClass.st[this.type])
  }

  "case class" must{
    "generate insert" in{
      val  generetedInsert = testCaseClass(1, "str", "str2").statement
      val checkingInsert = QueryBuilder.insertInto("name").value("a", 1).value("b", "str").value("c", "str2")
      assert(generetedInsert.getQueryString == checkingInsert.getQueryString)
    }
  }
}
