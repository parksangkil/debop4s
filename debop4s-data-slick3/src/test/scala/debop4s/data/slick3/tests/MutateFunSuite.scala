package debop4s.data.slick3.tests

import debop4s.core.concurrent._
import debop4s.data.slick3.AbstractSlickFunSuite

import debop4s.data.slick3.TestDatabase.driver.api._
import debop4s.data.slick3._
import debop4s.data.slick3.SlickContext._
import slick.backend.DatabasePublisher

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
 * MutateFunSuite
 * @author sunghyouk.bae@gmail.com
 */
class MutateFunSuite extends AbstractSlickFunSuite {

  test("mutate") {
    ifNotCapF(jcap.mutable) {
      cancel("not support mutate")
      Future()
    }

    class Data(tag: Tag) extends Table[(Int, String)](tag, "DATA") {
      def id = column[Int]("id", O.PrimaryKey)
      def data = column[String]("data")
      def * = (id, data)
    }
    val data = TableQuery[Data]

    var seenEndMarker = false
    db.exec {
      data.schema.create >>
      (data ++= Seq((1, "a"), (2, "b"), (3, "c"), (4, "d")))
    }

    val publisher = db.stream(data.mutate.transactionally)

    SlickContext.foreach(publisher) { m =>
      if (!m.end) {
        if (m.row._1 == 1) m.row = m.row.copy(_2 = "aa")
        else if (m.row._1 == 2) m.delete
        else if (m.row._1 == 3) m += ((5, "ee"))
      } else {
        seenEndMarker = true
      }
    }

    seenEndMarker shouldBe false
    db.run(data.sortBy(_.id).result).map(_ shouldBe Seq((1, "aa"), (3, "c"), (4, "d"), (5, "ee")))

    db.exec { data.schema.drop }
  }

  test("delete mutate") {
    class T(tag: Tag) extends Table[(Int, Int)](tag, "del_mutate_t") {
      def a = column[Int]("A")
      def b = column[Int]("B", O.PrimaryKey)
      def * = (a, b)
    }
    val ts = TableQuery[T]
    def tsByA = ts.findBy(_.a)

    var seenEndMarker = false
    val a = {
      ts.schema.create >>
      (ts ++= Seq((1, 1), (1, 2), (1, 3), (1, 4))) >>
      (ts ++= Seq((2, 5), (2, 6), (2, 7), (2, 8))) >>
      runnableStreamableCompiledQueryActionExtensionMethods(tsByA(1)).mutate(sendEndMarker = true).transactionally
    }

    SlickContext.foreach(db.stream(a)) { m =>
      if (!m.end) m.delete
      else {
        seenEndMarker = true
        m += ((3, 9))
      }
    }.await

    seenEndMarker shouldBe true
    db.result(ts).to[Set] shouldBe Set((2, 5), (2, 6), (2, 7), (2, 8), (3, 9))

    db.exec { ts.schema.drop }
  }

}