package debop4s.core.pool

import org.apache.commons.pool2.PooledObjectFactory
import org.apache.commons.pool2.impl.{GenericObjectPoolConfig, GenericObjectPool}
import org.slf4j.LoggerFactory

/**
 * AbstractObjectPool
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 12. 11. 오후 4:35
 */
abstract class AbstractObjectPool[T <: AnyRef](val config: GenericObjectPoolConfig,
                                               val factory: PooledObjectFactory[T]) extends AutoCloseable {

  private lazy val log = LoggerFactory.getLogger(getClass)

  protected val pool = new GenericObjectPool[T](factory, config)

  /**
   * 풀에서 리소스를 얻습니다.
   * @return 제공된 리소스
   */
  def getResource: T = {
    pool.borrowObject().asInstanceOf[T]
  }

  /**
   * 재사용을 위해 풀에 리소스를 반환합니다.
   * @param resource 반환할 리소스
   */
  def returnResource(resource: T) {
    returnResourceObject(resource)
  }

  /**
   * 재사용을 위해 풀에 리소스를 반환합니다.
   * @param resource 반환할 리소스
   */
  protected
  def returnResourceObject(resource: T) {
    pool.returnObject(resource)
  }

  /**
   * 재사용할 수 없는 리소스는 폐기하도록 합니다.
   * @param broken 재사용이 불가한 리소스
   **/
  def returnBrokenResource(broken: T) {
    returnBrokenResourceObject(broken)
  }

  /**
   * 재사용할 수 없는 리소스는 폐기하도록 합니다.
   * @param broken 재사용이 불가한 리소스
   **/
  protected
  def returnBrokenResourceObject(broken: T) {
    pool.invalidateObject(broken)
  }

  def destroy() = synchronized {
    if (!pool.isClosed) {
      pool.close()
      log.debug("Pool을 제거했습니다.")
    }
  }

  def close() {
    destroy()
  }
}
