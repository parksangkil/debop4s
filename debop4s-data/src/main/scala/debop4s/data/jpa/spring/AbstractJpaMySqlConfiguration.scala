package debop4s.data.jpa.spring

import debop4s.data._
import debop4s.data.jdbc.DataSources
import java.util.Properties
import javax.sql.DataSource
import org.hibernate.cfg.AvailableSettings
import org.springframework.context.annotation.Bean
import scala.collection.immutable.HashMap

/**
 * AbstractMySqlJpaConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 1. 9. 오후 4:08
 */
abstract class AbstractJpaMySqlConfiguration extends AbstractJpaConfiguration {

  @Bean
  override def dataSource: DataSource = {
    buildDataSource(DRIVER_CLASS_MYSQL,
      s"jdbc:mysql://localhost/$getDatabaseName",
      "root",
      "root")
  }

  override def jpaProperties: Properties = {
    val props: Properties = super.jpaProperties
    props.put(AvailableSettings.DIALECT, DIALECT_MYSQL)
    props
  }
}

/**
 * AbstractJpaMySqlHikariConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 1. 9. 오후 4:08
 */
abstract class AbstractJpaMySqlHikariConfiguration extends AbstractJpaMySqlConfiguration {

  @Bean
  override def dataSource: DataSource = {

    DataSources.getHikariDataSource(DATASOURCE_CLASS_MYSQL,
      "jdbc:mysql://localhost/" + getDatabaseName,
      "root",
      "root",
      defaultProperties)
  }

  def defaultProperties = {
    HashMap(
      "cachePrepStmts" -> "true",
      "prepStmtCacheSize" -> "500",
      "prepStmtCacheSqlLimit" -> "2048",
      "useServerPrepStmts" -> "true",
      "characterEncoding" -> "UTF-8",
      "useUnicode" -> "true"
    )
  }
}
