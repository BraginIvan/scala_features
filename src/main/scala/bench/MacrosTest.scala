package bench

import com.datastax.driver.core.querybuilder.{QueryBuilder, Insert}
import macroses.CaseClassToInsert
import macroses.CaseClassToInsert._
import org.joda.time.DateTime
import org.openjdk.jmh.annotations.{Fork, Scope, State, Benchmark}
import scala.collection.JavaConverters._

/**
 * Created by bragi_000 on 16.09.2015.
 */


case class TestCaseClass(
                          @transient firstColumn: Int,
                          mapStrDouble: Map[String, Double],
                          listStr: List[String],
                          optListStr: Option[List[String]],
                          date: DateTime,
                          mapStrBoolean: Map[String, Boolean],
                          a4: String,
                          a5: String,
                          a6: String,
                          a7: String,
                          a8: String,
                          a9: String,
                          a10: String,
                          ah: String,
                          af: String,
                          ai: String,
                          al1: String,
                          al5: String,
                          al7: String,
                          al9: String,
                          al8: String,
                          al6: String,
                          al3: String,
                          al4: String,
                          al2: String,
                          al0: String

                          ){

  val list = List(
    ("mapStrDouble", mapStrDouble)
    ,("listStr", listStr)
    ,("optListStr", optListStr)
    ,("date", date)
    ,("mapStrBoolean", mapStrBoolean)
    ,("dm7", a4)
    ,("dm8", a5)
    ,("dm9", a6)
    ,("dm0", a7)
    ,("dk0", a8)
    ,("dk1", a9)
    ,("dk2", a10)
    ,("dk3", ah)
    ,("dk4", af)
    ,("dk5", ai)
    ,("dk6", al1)
    ,("dk7", al5)
    ,("d1",  al7)
    ,("d2",  al9)
    ,("d3",  al8)
    ,("d4",  al6)
    ,("d5",  al3)
    ,("d03",  al4)
    ,("d04",  al2)
    ,("d05",  al1)
  )
  import Impl.InsertScalaValue
  def macrosStatement = CaseClassToInsert[this.type]("name")
  def macrosCopyStatement = list.foldLeft(QueryBuilder.insertInto("name"))((newInsert, next) =>  newInsert.scalaValue(next._1, next._2))
  def fastStatement = QueryBuilder.insertInto("name")
    .value("dm2", mapStrDouble.map {case (k, v) => k -> java.lang.Double.valueOf(v)})
    .value("listStr", listStr.asJava)
    .value("optListStr", optListStr.map(_.asJava).orNull)
    .value("date", date.toDate)
    .value("mapStrBoolean", mapStrBoolean.map {case (k, v) => k -> java.lang.Boolean.valueOf(v)})
    .value("dm7", a4)
    .value("dm8", a5)
    .value("dm9", a6)
    .value("dm0", a7)
    .value("dk0", a8)
    .value("dk1", a9)
    .value("dk2", a10)
    .value("dk3", ah)
    .value("dk4", af)
    .value("dk5", ai)
    .value("dk6", al1)
    .value("dk7", al5)
    .value("d1",  al7)
    .value("d2",  al9)
    .value("d3",  al8)
    .value("d4",  al6)
    .value("d5",  al3)
    .value("d03",  al4)
    .value("d04",  al2)
    .value("d05",  al1)
}

@Fork(1)
@State(Scope.Benchmark)
class MacrosTest {

val instance = TestCaseClass(1, Map(("5" -> 5d), ("6" -> 6d),("7" -> 7d),("8" -> 8d)), List("str2", "s", "d"), None, DateTime.now(),
  Map(("5" -> true), ("6" -> true),("7" -> false),("8" -> false)),
  "str2","str", "str2","str", "str2","str", "str2","str", "str2","str",
  "str2","str", "str2","str", "str2","str", "str2","str", "str2","str")

  @Benchmark
  def macros = instance.macrosStatement
  @Benchmark
  def macrosCopy = instance.macrosCopyStatement
  @Benchmark
  def fast = instance.fastStatement
 /* import Impl.InsertScalaValue
  @Benchmark
  def HelloWorld3 = List(
   ("d", 1),
   ("dm1", 1),
   ("dm2", "ffs"),
   ("dm3", "ffs"),
   ("dm4", "ffs"),
   ("dm5", "ffs"),
   ("dm6", "ffs"),
   ("dm7", "ffs"),
   ("dm8", "ffs"),
   ("dm9", "ffs"),
   ("dm0", "ffs"),
   ("dk0", "ffs"),
   ("dk1", "ffs"),
   ("dk2", "ffs"),
   ("dk3", "ffs"),
   ("dk4", "ffs"),
   ("dk5", "ffs"),
   ("dk6", "ffs"),
   ("dk7", "ffs"),
   ("d1", "fgjh"),
   ("d2", "fgjh"),
   ("d3", "fgjh"),
   ("d4", "fgjh"),
   ("d5", "fgjh")
 ).foldLeft(QueryBuilder.insertInto("name"))((newInsert, next) =>  newInsert.scalaValue(next._1, next._2))*/
}
