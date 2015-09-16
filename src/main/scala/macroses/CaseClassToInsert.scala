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

  def apply[T](tableName: String, dontUse: List[String]):Insert = macro st_impl[T]

  def st_impl[A : c.WeakTypeTag](c: Context)(tableName: c.Expr[String], dontUse : c.Expr[List[String]]): c.Expr[Insert] = {

    implicit class ColumnLowCase(str: String){
      def toColumnCase:String =
        if(str == "id")
          "uuid"
        else
          str.replaceAll("(\\p{Lower})(\\p{Upper})","$1_$2").toLowerCase

    }

    import c.universe._

    val du =
      (dontUse.tree match {
        case Apply(_, l) => l
      }).map{case Literal(Constant(s: String)) => s}


    val parametrs = c.weakTypeOf[A].typeSymbol.
      typeSignature.decls.toList.filterNot(_.isMethod)
      .map(_.name.toString.replaceAll(" ", "")).filterNot(du.contains)
    val listApply = Select(reify(List).tree, TermName("apply"))


      def toScala[T](value: Any): Any = value match {
        case list: List[T] => list.asJava
        case time: DateTime =>  time.toDate
        case opt: Option[_] => opt match {
          case Some(mapDouble: Map[String, Double]) =>  mapDouble.map { case (k, v) => k -> java.lang.Double.valueOf(v) }.asJava
          case Some(listString: List[T]) =>  listString.asJava
          case Some(str: String) =>  str
          case _@mayBe =>  mayBe.orNull
        }
        case  _ @ smth => "hello".foreach(println)
           smth
      }

    c.Expr[Insert](
      q"""

         import com.datastax.driver.core.querybuilder.QueryBuilder

          ${parametrs.map(v =>(v.toColumnCase, TermName(v)))}
          .foldLeft(QueryBuilder.insertInto(${reify(tableName.splice)}))((newInsert, next)
         =>   newInsert.scalaValue(next._1, next._2)
         )""")

  }
}
