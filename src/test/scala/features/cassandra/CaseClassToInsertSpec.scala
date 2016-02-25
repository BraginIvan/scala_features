package features.cassandra


import cassandra.Connector
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import org.joda.time.DateTime
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */
import scala.collection.JavaConverters._


class CaseClassToInsertSpec extends WordSpecLike{

  val instance = MyClass(1, "str", "str2")

 val  v = 0
  "case class" must{
    "generate insert" in{
      val generatedInsert = instance.statement
      val checkingInsert = QueryBuilder.insertInto("makros").value("first_column", 1).value("b", "str").value("c", "str2")
      assert(generatedInsert.toString == checkingInsert.toString)
      Connector.session.execute(generatedInsert)
      val a = 0
    }



  }



}
