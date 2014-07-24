package taot.debug

import language.experimental.macros

import reflect.macros.Context

object DebugMacros {

  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._    // Give convenient access to AST classes
    reify { println("Hello world!") }    // turns the given code to Expr[T]. reify is also a macro - a macro used when compiling macros
  }

  def printparam(param: Any): Unit = macro printparam_impl

  def printparam_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify { println(param.splice) }    // splice, a special method of Expr, can only be used inside a reify call
  }

  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    val paramRep = show(param.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)

    val typeRepTree = Literal(Constant(param.actualType.toString))
    val typeRepExpr = c.Expr[String](typeRepTree)

    //reify { println(paramRepExpr.splice + " = " + param.splice ) }
    reify {
      println(s"${paramRepExpr.splice}: ${typeRepExpr.splice} = ${param.splice}" )
    }
  }

  def debug2(params: Any*): Unit = macro debug2_impl

  def debug2_impl(c: Context)(params: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._

    val trees = params.map { param =>
      param.tree match {
        case Literal(Constant(const)) => {
          val reified = reify { print(param.splice) }
          reified.tree
        }
        case _ => {
          val paramRep = show(param.tree)
          val paramRepTree = Literal(Constant(paramRep))
          val paramRepExpr = c.Expr[String](paramRepTree)
          val reified = reify { print(paramRepExpr.splice + " = " + param.splice) }
          reified.tree
        }
      }
    }

    val separators = (1 to (trees.size - 1)).map { _ => (reify { print(", ")}).tree } :+ (reify { println() }).tree
    val treesWithSeparators = trees.zip(separators).flatMap(p => List(p._1, p._2))

    c.Expr[Unit](Block(treesWithSeparators.toList, Literal(Constant())))
  }
}
