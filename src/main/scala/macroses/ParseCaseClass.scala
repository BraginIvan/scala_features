package macroses

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

/**
 * Created by ivan on 9/11/15.
 */
object ParseCaseClass {

  def st[T]:List[(String,Any)] = macro st_impl[T]

  def st_impl[A : c.WeakTypeTag](c: Context): c.Expr[List[(String,Any)]] = {
    import c.universe._
    val methods: List[String] = c.weakTypeOf[A].typeSymbol.
      typeSignature.decls.toList.filterNot(_.isMethod)
      .map(_.name.toString)
    val listApply = Select(reify(List).tree, TermName("apply"))

    c.Expr[List[(String,Any)]](q""" ${methods.map(v => (v, TermName(v)))} """)
  }
}
