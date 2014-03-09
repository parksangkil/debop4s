package com.github.debop4s.data.tests.jpa.config

import com.github.debop4s.data.jpa.spring.AbstractJpaH2Configuration
import java.util.Properties
import org.hibernate.cfg.AvailableSettings
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * ScalaJpaConfiguration 
 *
 * @author sunghyouk.bae@gmail.com
 * @since 2014. 2. 28.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackages = Array("com.github.debop4s.data.jpa"))
class ScalaJpaConfiguration extends AbstractJpaH2Configuration {

  override def getMappedPackageNames: Array[String] =
    Array(classOf[ScalaJpaEntity].getPackage.getName)

  override def jpaProperties: Properties = {
    val props = super.jpaProperties
    props.put(AvailableSettings.HBM2DDL_AUTO, "create")
    props
  }
}
