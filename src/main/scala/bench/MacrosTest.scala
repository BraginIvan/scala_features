package bench

import com.datastax.driver.core.querybuilder.{QueryBuilder, Insert}
import macroses.CaseClassToInsert
import macroses.CaseClassToInsert._
import org.joda.time.DateTime
import org.openjdk.jmh.annotations.{State, Benchmark}

/**
 * Created by bragi_000 on 16.09.2015.
 */


case class TestCaseClass(
                          @transient firstColumn: Int,
                          b: String,
                          c: String,
                          a: String,
                          a1: String,
                          a3: String,
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
  import Impl.InsertScalaValue
  def statement = CaseClassToInsert[this.type]("name")
  def statement2 = QueryBuilder.insertInto("name")
    .scalaValue("d", 1)
    .scalaValue("dm1", 1)
    .scalaValue("dm2", "ffs")
    .scalaValue("dm3", "ffs")
    .scalaValue("dm4", "ffs")
    .scalaValue("dm5", "ffs")
    .scalaValue("dm6", "ffs")
    .scalaValue("dm7", "ffs")
    .scalaValue("dm8", "ffs")
    .scalaValue("dm9", "ffs")
    .scalaValue("dm0", "ffs")
    .scalaValue("dk0", "ffs")
    .scalaValue("dk1", "ffs")
    .scalaValue("dk2", "ffs")
    .scalaValue("dk3", "ffs")
    .scalaValue("dk4", "ffs")
    .scalaValue("dk5", "ffs")
    .scalaValue("dk6", "ffs")
    .scalaValue("dk7", "ffs")
    .scalaValue("d1", "fgjh")
    .scalaValue("d2", "fgjh")
    .scalaValue("d3", "fgjh")
    .scalaValue("d4", "fgjh")
    .scalaValue("d5", "fgjh")
}

class MacrosTest {



/*  @Benchmark
  def helloWorld = TestCaseClass(1, "str", "str2","str", "str2","str",
    "str2","str", "str2","str", "str2","str", "str2","str", "str2","str",
    "str2","str", "str2","str", "str2","str", "str2","str", "str2","str").statement
  import Impl.InsertScalaValue*/
  @Benchmark
  def helloWorld2 = TestCaseClass(1, "str", "str2","str", "str2","str",
    "str2","str", "str2","str", "str2","str", "str2","str", "str2","str",
    "str2","str", "str2","str", "str2","str", "str2","str", "str2","str").statement
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
