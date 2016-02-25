package cassandra

import java.net.InetSocketAddress

import com.datastax.driver.core.{PoolingOptions, SocketOptions, Cluster}
import com.datastax.driver.core.policies.{Policies, WhiteListPolicy}

/**
 * Created by ivan on 2/25/16.
 */
object Connector {
  private lazy val cluster = Cluster.builder()
    .addContactPoints("localhost")
    .build()
  val session = cluster.connect("graph")
}
