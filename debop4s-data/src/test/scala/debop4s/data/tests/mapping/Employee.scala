package debop4s.data.tests.mapping

import debop4s.core.utils.{ToStringHelper, Hashs}
import debop4s.data.model.{UpdatedTimestampEntity, HibernateEntity}
import java.lang.{Long => jLong}
import javax.persistence._
import org.hibernate.annotations.{CacheConcurrencyStrategy, Type, DynamicUpdate, DynamicInsert}
import org.joda.time.DateTime

/**
 * Employee
 *
 * @author sunghyouk.bae@gmail.com
 * @since 2014. 2. 26.
 */
@Entity
@org.hibernate.annotations.Cache(region = "common", usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq")
@Access(AccessType.FIELD)
@SerialVersionUID(6936596398947403315L)
class Employee extends HibernateEntity[jLong] with UpdatedTimestampEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_seq")
  @Column(name = "employeeId")
  protected var id: jLong = _

  def getId: jLong = id

  @Column(name = "empNo", nullable = false, length = 32)
  var empNo: String = _

  @Column(name = "employeeName", nullable = false, length = 32)
  var name: String = _

  @Column(name = "employeeEmail", length = 64)
  var email: String = _

  @Type(`type` = "debop4s.data.hibernate.usertype.JodaDateTimeUserType")
  var birthDay: DateTime = _

  @Embedded
  var address: Address = Address()

  override def hashCode(): Int = Hashs.compute(empNo, name)

  override protected def buildStringHelper: ToStringHelper =
    super.buildStringHelper
      .add("empNo", empNo)
      .add("name", name)
      .add("email", email)
}
