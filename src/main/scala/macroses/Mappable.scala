package macroses

import com.datastax.driver.core.Row

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

trait Mappable[T]{
  def fromMap(row: Row): T
}

object Mappable
{
  implicit def materializeMappable[T]: Mappable[T] = macro materializeMappableImpl[T]

  implicit class ColumnLowCase(str: String){
    def toColumnCase:String =  str.replaceAll("(\\p{Lower})(\\p{Upper})","$1_$2").toLowerCase
  }

  def materializeMappableImpl[T: c.WeakTypeTag](c: Context): c.Expr[Mappable[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion
    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor ⇒ m
    }.get.paramLists.head

    val fromMapParams = fields.map { field ⇒
      val name = field.asTerm.name
      val decoded = name.decodedName.toString
      val returnType = tpe.decl(name).typeSignature
      q"row.getObject(${decoded.toColumnCase}).asInstanceOf[$returnType]"
    }

    c.Expr[Mappable[T]] { q"""
        new Mappable[$tpe] {
        def fromMap(row:  com.datastax.driver.core.Row): $tpe = $companion(..$fromMapParams)
        }
        """
    }
  }
}