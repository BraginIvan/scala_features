package features.cassandra

import macroses.CaseClassToInsert

/**
 * Created by ivan on 2/25/16.
 */
case class MyClass(
                    firstColumn: Int,
                    b: List[String],
                    c: Option[String],
                    s: Int,
                    map: Map[String, Double]
                    ){

  import bench.Impl.InsertScalaValue
  def statement = CaseClassToInsert[this.type]("macros")
}
