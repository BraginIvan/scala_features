package features.cassandra

import com.datastax.driver.core.querybuilder.QueryBuilder

/**
 * Created by ivan on 9/11/15.
 */
trait CaseClassToInsert {
  def prepareStatement(tableName:String, list :List[(String,Any)]) = {
    list.foldLeft(QueryBuilder.insertInto(tableName))((newInsert, next) => newInsert.value(next._1, next._2))
  }
}
