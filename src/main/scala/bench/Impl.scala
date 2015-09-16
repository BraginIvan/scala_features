package bench

import com.datastax.driver.core.querybuilder.Insert
import org.joda.time.DateTime
import scala.collection.JavaConverters._

/**
 * Created by bragi_000 on 16.09.2015.
 */
object Impl {
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
}
