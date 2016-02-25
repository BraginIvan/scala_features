package features.cassandra

import cassandra.Connector
import com.datastax.driver.core.Row
import macroses.{Mappable, TableToCaseClass}
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 2/25/16.
 */
class GetAsCaseClass extends WordSpecLike{


  val row = Connector.session.execute("select * from makros where first_column = 1").one()

  case class Person(name: String, age: Int)

  def materialize[T: Mappable](map: Row) = implicitly[Mappable[T]].fromMap(map)

  assert {
    materialize[MyClass](row) == MyClass(1, "str1", "str2")
  }


}


