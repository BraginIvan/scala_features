package features.cassandra

import macroses.CaseClassToInsert

/**
 * Created by ivan on 2/25/16.
 */
case class MyClass2(
                    firstColumn: Int,
                    d: String,
                    e: String){

  import bench.Impl.InsertScalaValue
  def statement = CaseClassToInsert[this.type]("makros")
}
