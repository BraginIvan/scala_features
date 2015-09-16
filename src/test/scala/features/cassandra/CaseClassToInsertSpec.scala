package features.cassandra


import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import macroses.CaseClassToInsert
import org.joda.time.DateTime
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */
import scala.collection.JavaConverters._


class CaseClassToInsertSpec extends WordSpecLike{




  case class testCaseClass(
                            @transient firstColumn: Int,
                            b: String,
                            c: String){

    import bench.Impl.InsertScalaValue

    def statement = CaseClassToInsert[this.type]("name")
  }
  val instance = testCaseClass(1, "str", "str2")


  "case class" must{
    "generate insert" in{
      val generatedInsert = instance.statement
      val checkingInsert = QueryBuilder.insertInto("name").value("first_column", 1).value("b", "str").value("c", "str2")
      assert(generatedInsert.getQueryString == checkingInsert.getQueryString)
      val a = 0
    }



  }



}
