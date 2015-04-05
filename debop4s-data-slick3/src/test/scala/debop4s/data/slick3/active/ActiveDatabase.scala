package debop4s.data.slick3.active

import debop4s.data.slick3._
import debop4s.data.slick3.model.{Versionable, IntEntity}
import debop4s.data.slick3.schema.SlickComponent

/**
 * ActiveDatabase
 * @author sunghyouk.bae@gmail.com
 */
object ActiveDatabase extends SlickComponent with ActiveQueryExtensions with ActiveSchema

trait ActiveQueryExtensions {
  this: SlickComponent with ActiveSchema =>

  import driver.api._

  implicit class SupplierQueryExt(query: TableQuery[Suppliers]) extends VersionableTableExtensions[Supplier, Int](query)

  implicit class SupplierExtensions(self: Supplier) {
    def save(implicit sess: Session): Supplier = suppliers.save(self)
    def delete(implicit sess: Session): Boolean = {
      db.exec(beers.filter(_.supplierId === self.id).delete)
      suppliers.deleteEntity(self)
    }
  }

  implicit class BeerQueryExt(query: TableQuery[Beers]) extends IdTableExtensions[Beer, Int](query)

  implicit class BeerExtensions(self: Beer) {

    def save: Beer = beers.save(self)
    def delete: Boolean = beers.deleteEntity(self)

    def supplier: Option[Supplier] =
      suppliers.findOptionById(self.supplierId)

    def friendBeers: Seq[Beer] = {
      val q = for {
        (s, b) <- suppliers join beers on (_.id === _.supplierId) if s.id === self.supplierId
      } yield b

      db.result(q).toSeq.asInstanceOf[Seq[Beer]]
    }
  }

}

trait ActiveSchema {
  this: SlickComponent =>

  import driver.api._

  case class Beer(name: String,
                  price: Double,
                  supplierId: Int,
                  var id: Option[Int] = None) extends IntEntity

  case class Supplier(name: String,
                      var id: Option[Int] = None,
                      var version: Long = 0) extends IntEntity with Versionable

  class Suppliers(tag: Tag) extends IdVersionTable[Supplier, Int](tag, "active_suppliers") {
    def id = column[Int]("sup_id", O.PrimaryKey, O.AutoInc)
    def version = column[Long]("version", O.Default(0L))
    def name = column[String]("sup_name", O.Length(254, true))

    def * = (name, id.?, version) <>(Supplier.tupled, Supplier.unapply)

    def getBeers = beers.filter(_.supplierId === id)
  }
  lazy val suppliers = TableQuery[Suppliers]

  class Beers(tag: Tag) extends IdTable[Beer, Int](tag, "active_beers") {
    def id = column[Int]("beer_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("beer_name", O.Length(254, true))
    def price = column[Double]("beer_price")
    def supplierId = column[Int]("supplier_id")

    def * = (name, price, supplierId, id.?) <>(Beer.tupled, Beer.unapply)

    def supplierFK = foreignKey("fk_beers_suppliers", supplierId, suppliers)(_.id)
  }
  lazy val beers = TableQuery[Beers]

  lazy val schema = suppliers.schema ++ beers.schema

  def createSchema() = {
    LOG.info(s"Create Active Database schema...")

    LOG.debug(s"Schema Drop:\n${ schema.dropStatements.mkString("\n") }")
    LOG.debug(s"Schema Create:\n${ schema.createStatements.mkString("\n") }")

    db.seq(
      schema.drop.asTry,
      schema.create
    )
  }

  def dropSchema() = {
    LOG.info(s"Drop Active Database schema...")
    LOG.debug(s"Schema Drop:\n${ schema.dropStatements.mkString("\n") }")

    db.exec {
      schema.drop.asTry
    }
  }
}


