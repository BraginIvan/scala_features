package features.cassandra


import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import macroses.CaseClassToInsert
import org.joda.time.DateTime
import org.scalatest.WordSpecLike

/**
 * Created by ivan on 9/11/15.
 */
import scala.collection.JavaConverters._

case class MyClass(a:String)

class CaseClassToInsertSpec extends WordSpecLike{


  implicit class InsertScalaValue(x: Insert){
    def scalaValue[T](name: String, value: T): Insert =  value match {
      case list: List[T] =>  x.value(name,  if(list.isEmpty) null else list.asJava)
      case time: DateTime => x.value(name, time.toDate)
      case opt: Option[_] => opt match {
        case Some(mapDouble: Map[String, _]) => x.value(name,mapDouble.map { case (k, v) => k -> {v match {
          case d:Double => java.lang.Double.valueOf(d)
          case b:Boolean => java.lang.Boolean.valueOf(b)
          case _ => v
        } }}.asJava)
        case Some(listString: List[T]) =>  x.value(name,listString.asJava)
        case Some(str: String) =>x.value(name, str)
        case _@mayBe => x.value(name, mayBe.orNull)
      }
      case _@smth => x.value(name,smth)
    }

  }
  trait a {
    def statement : Insert
    def getStatement[T](tableName : String): Insert =  CaseClassToInsert[T](tableName, List("b"))
  }

  case class testCaseClass(firstColumn: Int, b: String, c: String) extends a{
    def statement = getStatement[testCaseClass.type ]("name")
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
