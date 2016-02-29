package features.cassandra

import cassandra.Connector
import com.datastax.driver.core.Row
import macroses.{TableToCaseClass, TableToCaseClass3}
import org.scalatest.WordSpecLike
import scala.collection.JavaConversions._
import spray.json._

/**
 * Created by ivan on 2/25/16.
 */
class GetAsCaseClass extends WordSpecLike{

  implicit def toIntegerList( lst: List[java.lang.Integer] ): List[Int] = lst.map(_.toInt)
  implicit def toBooleanList( lst: List[java.lang.Boolean] ): List[Boolean] = lst.map(_.booleanValue())
  implicit def toIntegerMap( map: Map[String, java.lang.Integer] ): Map[String, Int] = map.map(t => t._1 -> t._2.toInt)
  implicit def toBooleanMap( map: Map[String, java.lang.Boolean] ): Map[String, Boolean] = map.map(t => t._1 -> t._2.booleanValue())
  implicit def toDoubleMap( map: Map[String, java.lang.Double] ): Map[String, Double] = map.map(t => t._1 -> t._2.doubleValue())
  implicit def toOptionBooleanMap( map: Option[Map[String, java.lang.Boolean]] ): Option[Map[String, Boolean]] = map.map(_.map(t => t._1 -> t._2.booleanValue()))

  val j = Option(null)


  val row = Connector.session.execute("select * from macros where first_column = 1").one()
  row.getMap[String, Double]("", classOf[String], classOf[Double]).toMap
  val res = TableToCaseClass[MyClass]

  val k = 0
}


