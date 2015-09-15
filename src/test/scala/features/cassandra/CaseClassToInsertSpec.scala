package features.cassandra

import com.datastax.driver.core.querybuilder.QueryBuilder
import macroses.ParseCaseClass
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */
class CaseClassToInsertSpec extends WordSpecLike{

  case class testCaseClass(firstColumn: Int, b: String, c: String) extends CaseClassToInsert{
    def statement = prepareStatement("name", ParseCaseClass[this.type])
  }
  val instance = testCaseClass(1, "str", "str2")


  "case class" must{
    "generate insert" in{
      val generatedInsert = instance.statement
      val checkingInsert = QueryBuilder.insertInto("name").value("first_column", 1).value("b", "str").value("c", "str2")
      assert(generatedInsert.getQueryString == checkingInsert.getQueryString)
    }



  }



}
