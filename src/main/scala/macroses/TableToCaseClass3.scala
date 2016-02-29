package macroses

import com.datastax.driver.core.Row
import com.datastax.driver.core.querybuilder.Insert

import scala.language.experimental.macros
import scala.reflect.ClassTag
import scala.reflect.macros.whitebox.Context
import scala.reflect.ClassTag
import scala.reflect._
import scala.collection.JavaConversions._

/**
 * Created by ivan on 9/11/15.
 */
object TableToCaseClass3 {

  def instantiate[T](klass: Class[T]): Seq[Any] => T = {
    args:Seq[Any] =>
      val clazz = Class.forName(klass.getName)
      val constructor = clazz.getConstructors()(0)
      val workArg:Array[AnyRef] = new Array(args.length)
      var i=0
      for(arg <- args){
        workArg(i) = arg match{
          case i:AnyRef => i
          case i:Int => new java.lang.Integer(i)
          case i:Long => new java.lang.Long(i)
          case i:Float => new java.lang.Float(i)
          case i:Double => new java.lang.Double(i)
          case i:Boolean => new java.lang.Boolean(i)
          case i:Char => new java.lang.Character(i)
          case i:Byte => new java.lang.Byte(i)
          case i:Short => new java.lang.Short(i)
          case _ => arg.asInstanceOf[AnyRef]
        }
        i = i + 1
      }
      constructor.newInstance(workArg:_*).asInstanceOf[T]
  }

  def apply[T](klass: Class[T], row: Row):T = macro st_impl[T]

  def st_impl[A : c.WeakTypeTag](c: Context)(klass: c.Expr[Class[A]], row: c.Expr[Row]): c.Expr[A] = {

    implicit class ColumnLowCase(str: String){
      def toColumnCase:String =  str.replaceAll("(\\p{Lower})(\\p{Upper})","$1_$2").toLowerCase
    }


    import c.universe._

    val parametrs = c.weakTypeOf[A].typeSymbol.
      typeSignature.decls.toList.filterNot(_.isMethod)
      .map(a => a.typeSignature.toString ->  a.name.toString.replaceAll(" ", "").toColumnCase)
    /*
        parametrs.map { case (t: Type, value: String) =>
          t match {
            case "Int" => Some((_:Row).getObject(value)).map(_.asInstanceOf[Int])
          }
        }*/
    val g = Some(parametrs)

    c.Expr[A](
      q"""
         val r = ${reify{row.splice}}
         val i = ${reify{instantiate(klass.splice)}}
         $g.map(rr => i.apply(Seq(r.getInt(rr.head._2), r.getString("b"), r.getString("c")))).get

        """
    )


  }
}
