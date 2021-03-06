package debop4s.data.tests.mapping

import javax.persistence.{AccessType, Access, Embeddable}


@Embeddable
@Access(AccessType.FIELD)
case class Address(street: String = null,
                   city: String = null,
                   state: String = null,
                   country: String = null,
                   zipcode: String = null) {

  def this() {
    this(null, null, null, null, null)
  }
}
