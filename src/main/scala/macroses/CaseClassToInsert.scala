package macroses

import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import org.joda.time.DateTime

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context
import scala.collection.JavaConverters._
import com.datastax.driver.core.querybuilder.{Insert, QueryBuilder}
import scala.collection.JavaConverters._
import org.joda.time.DateTime

/**
 * Created by ivan on 9/11/15.
 */
object CaseClassToInsert {

  def apply[T](tableName: String):Insert = macro st_impl[T]

  def st_impl[A : c.WeakTypeTag](c: Context)(tableName: c.Expr[String]): c.Expr[Insert] = {

    implicit class ColumnLowCase(str: String){
      def toColumnCase:String =  str.replaceAll("(\\p{Lower})(\\p{Upper})","$1_$2").toLowerCase
    }

    import c.universe._

    val parametrs = c.weakTypeOf[A].typeSymbol.
      typeSignature.decls.toList.filterNot(_.isMethod).filterNot(_.annotations.exists(
      _.tree.tpe =:= typeOf[scala.transient]
    )).map(_.name.toString.replaceAll(" ", ""))

    c.Expr[Insert](
      q"""
         import com.datastax.driver.core.querybuilder.QueryBuilder
          ${parametrs.map(v =>(v.toColumnCase, TermName(v)))}
          .foldLeft(QueryBuilder.insertInto(${reify(tableName.splice)}))((newInsert, next)
         =>   newInsert.scalaValue(next._1, next._2)
         )""")

  }
}
