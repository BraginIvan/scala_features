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

  val instance = MyClass(1, List("str"), Some("str2"), 10, Map("5" -> 7.0))

 val  v = 0
  "case class" must{
    "generate insert" in{
      val generatedInsert = instance.statement
      Connector.session.execute(generatedInsert)
      val a = 0
    }



  }



}
