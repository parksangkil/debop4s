package debop4s.data.jpa.spring

import debop4s.data._
import javax.sql.DataSource
import org.springframework.context.annotation.Bean

/**
 * AbstractMariaDbJpaConfiguration
 * Created by debop on 2014. 1. 29..
 */
abstract class AbstractJpaMariaDbConfiguration extends AbstractJpaMySqlConfiguration {

  @Bean
  override def dataSource: DataSource = {
    buildDataSource(DRIVER_CLASS_MARIADB,
      s"jdbc:mysql://localhost/$getDatabaseName",
      "root",
      "root")
  }
}
