package debop4s.core.io

import debop4s.core.io.model.{Company, User}
import debop4s.core.{AbstractCoreTest, YearWeek}

/**
 * AbstractSerializerTest
 * Created by debop on 2014. 3. 23.
 */
abstract class AbstractSerializerTest extends AbstractCoreTest {

  def serializer: Serializer

  test("serialize/deserialize") {
    val yearWeek = new YearWeek(2000, 1)
    val copied = serializer.deserialize[YearWeek](serializer.serialize(yearWeek), classOf[YearWeek])

    assert(copied != null)
    assert(copied.equals(yearWeek))
  }

  test("deep serialize reference type") {

    val company: Company = new Company
    company.code = "HCT"
    company.name = "HealthConnect"
    company.amount = 10000L
    company.description = "헬스커넥트"

    for (i <- 0 until 100) {
      val user = new User()
      user.name = "USER_" + i
      user.empNo = "EMPNO_" + i
      user.address = "ADDR_" + i
      company.users.add(user)
    }
    log.debug(s"user count = ${company.users.size}")

    val copied: Company = serializer.deserialize[Company](serializer.serialize(company), classOf[Company])
    assert(copied != null)
    assert(copied.users.size == 100)
    assert(copied == company)

    for (i <- 0 until copied.users.size) {
      assert(copied.users.get(i) == company.users.get(i))
    }
  }

  test("case class serialize") {
    val user = UserEntity(0, "debop")

    val copied = serializer.deserialize(serializer.serialize(user), classOf[UserEntity])
    copied should not be null
    copied shouldEqual user
  }


  trait Entity[T] extends Serializable {
    def id: T
  }

  case class UserEntity(override val id: Long, name: String) extends Entity[Long] with Serializable

  case class CompanyEntity(override val id: Long, name: String, code: String) extends Entity[Long]
}
