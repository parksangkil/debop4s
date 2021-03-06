package debop4s.data.tests.jpa.config

import debop4s.data.jpa.repository.JpaDao
import debop4s.data.tests.AbstractDataTest
import javax.persistence.{EntityManager, PersistenceContext, EntityManagerFactory}
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.context.{TestContextManager, ContextConfiguration}
import org.springframework.transaction.annotation.Transactional

/**
 * ScalaJpaTest 
 *
 * @author sunghyouk.bae@gmail.com
 * @since 2014. 2. 28.
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[ScalaJpaConfiguration]), loader = classOf[AnnotationConfigContextLoader])
@Transactional
class ScalaJpaTest extends AbstractDataTest {

  @Autowired val emf: EntityManagerFactory = null
  @PersistenceContext val em: EntityManager = null
  @Autowired val dao: JpaDao = null

  // NOTE: TestContextManager#prepareTestInstance 를 실행시켜야 제대로 Dependency Injection이 됩니다.
  new TestContextManager(getClass).prepareTestInstance(this)

  @Test
  def setupTest() {
    assert(emf != null)
    assert(em != null)
    assert(dao != null)
  }

  @Test
  def crud() {
    val entity = new ScalaJpaEntity
    entity.name = "Sunghyouk Bae"

    dao.persist(entity)
    dao.flush()
    dao.clear()

    val loaded = dao.findOne(classOf[ScalaJpaEntity], entity.id)
    assert(loaded != null)
    assert(loaded == entity)
    assert(loaded.isPersisted)
    assert(loaded.id > 0)
    println(loaded.toString)
  }

}
