package macroses

import com.datastax.driver.core.Row
import org.joda.time.DateTime

import scala.collection.immutable
import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context




object TableToCaseClass
{
  def apply[T]: T = macro implementetion[T]

  implicit class ColumnLowCase(str: String){
    def toColumnCase:String =  str.replaceAll("(\\p{Lower})(\\p{Upper})","$1_$2").toLowerCase
  }

  def implementetion[T: c.WeakTypeTag](c: Context): c.Expr[T] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion
    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
    }.get.paramLists.head

    val fromMapParams = fields.map { field ⇒
      val name = field.asTerm.name

      val decoded = name.decodedName.toString.toColumnCase
      val returnType = tpe.decl(name).typeSignature.finalResultType

      def getListInnerType(ty: c.Type) ={
        ty match {
          case t if t =:= typeOf[List[Int]] => typeOf[Integer]
          case t if t =:= typeOf[List[Boolean]] => typeOf[java.lang.Boolean]
          case _ => ty.typeArgs.head
        }
      }

      def getOptionInnerType(ty: c.Type) = ty.dealias.typeArgs.head


      def getMapValueTypes(ty: c.Type) =  ty.typeArgs.last match {
        case t if t =:= typeOf[Int] => typeOf[java.lang.Integer]
        case t if t =:= typeOf[Boolean] => typeOf[java.lang.Boolean]
        case t if t =:= typeOf[Double] => typeOf[java.lang.Double]
        case _ => ty.typeArgs.head
      }

      def getExtractor(ty: c.Type): c.Tree = {
        ty match {
          case t if t <:< typeOf[Map[String, Any]] =>
            q"""row.getMap[String, ${getMapValueTypes(ty)}]($decoded, classOf[String], classOf[${getMapValueTypes(ty)}]).toMap"""

          case t if t.toString.startsWith("Array[") =>
            q"""
              val macrosStr = row.getString($decoded)
              if(macrosStr == null)
                null
              else
                macrosStr.parseJson.convertTo[$ty]
            """

          case t if t <:< typeOf[List[JsonSerializable]] =>
            q"""row.getList($decoded, classOf[String]).toList.map(_.parseJson.convertTo[${getListInnerType(ty)}])"""

          case t if t <:< typeOf[List[Any]] => q"""row.getList($decoded, classOf[${getListInnerType(ty)}]).toList"""

          case t if t <:< typeOf[Option[Any]] => q"""Option(${getExtractor(getOptionInnerType(ty))})"""

          case t if t <:< typeOf[JsonSerializable] =>
            q"""
              val macrosStr = row.getString($decoded)
              if(macrosStr == null)
                null
              else
                macrosStr.parseJson.convertTo[$ty]
            """

          case t if t =:= typeOf[DateTime] => q"""new DateTime(row.getDate($decoded))"""

          case _ => q"""row.getObject($decoded).asInstanceOf[$ty]"""
        }
      }
      getExtractor(returnType)



    }
    c.Expr[T] { q"""
                import com.kountable.analytics.tools.JavaCollectionsConversions._
                import scala.collection.JavaConversions._
                $companion(..$fromMapParams)
        """
    }
  }


}