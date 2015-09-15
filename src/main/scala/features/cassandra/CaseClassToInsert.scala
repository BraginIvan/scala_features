package features.cassandra

import com.datastax.driver.core.querybuilder.QueryBuilder
import macroses.ParseCaseClass
import macroses.ParseCaseClass._

/**
 * Created by ivan on 9/11/15.
 */
trait CaseClassToInsert {
  implicit class ColumnLowCase(str: String){
    def toColumnCase:String =  str.foldLeft("")((newName, symbol) => newName + (if (symbol.isUpper) s"_${symbol.toLower}" else symbol))
  }

  def prepareStatement(tableName:String, list :List[(String,Any)]) = {
    list.foldLeft(QueryBuilder.insertInto(tableName))((newInsert, next) => newInsert.value(
      next._1.toColumnCase,
      next._2))
  }

}
