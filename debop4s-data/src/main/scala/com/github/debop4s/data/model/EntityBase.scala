package com.github.debop4s.data.model

import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, DynamicUpdate, DynamicInsert}

@MappedSuperclass
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
abstract class LongEntity extends HibernateEntity[java.lang.Long] {

  @Id
  @GeneratedValue
  var id: java.lang.Long = _

  override def getId = id

}

@MappedSuperclass
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
abstract class IntEntity extends HibernateEntity[java.lang.Integer] {

  @Id
  @GeneratedValue
  var id: java.lang.Integer = _

  override def getId = id

}

@MappedSuperclass
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
abstract class UuidEntity extends HibernateEntity[java.util.UUID] {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  var id: java.util.UUID = _

  override def getId = id
}

@MappedSuperclass
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
abstract class StringEntity extends HibernateEntity[String] {

  @Id
  var id: String = _

  override def getId = id

}