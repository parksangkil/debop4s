package org.hibernate.cache.rediscala.strategy

import org.hibernate.cache.rediscala.regions.{RedisNaturalIdRegion, RedisEntityRegion, RedisCollectionRegion}
import org.hibernate.cache.spi.access.{NaturalIdRegionAccessStrategy, SoftLock, CollectionRegionAccessStrategy, EntityRegionAccessStrategy}
import org.hibernate.cfg.Settings

/**
 * NonStrictReadWriteRedisCollectionRegionAccessStrategy
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 2. 21. 오후 1:19
 */
class NonStrictReadWriteRedisCollectionRegionAccessStrategy(private[this] val _region: RedisCollectionRegion,
                                                            private[this] val _settings: Settings)
    extends AbstractRedisAccessStrategy(_region, _settings)
    with CollectionRegionAccessStrategy {

    override def getRegion = region

    override def get(key: Any, txTimestamp: Long): AnyRef =
        region.get(key).asInstanceOf[AnyRef]

    override def putFromLoad(key: Any,
                             value: Any,
                             txTimestamp: Long,
                             version: Any,
                             minimalPutOverride: Boolean): Boolean = {
        if (minimalPutOverride && region.contains(key)) {
            return false
        }

        region.put(key, value)
        true
    }

    override def lockItem(key: Any, version: Any): SoftLock = null

    override def unlockItem(key: Any, lock: SoftLock) {
        region.remove(key)
    }
}

/**
 * NonStrictReadWriteRedisEntityRegionAccessStrategy
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 2. 21. 오후 1:16
 */
class NonStrictReadWriteRedisEntityRegionAccessStrategy(private[this] val _region: RedisEntityRegion,
                                                        private[this] val _settings: Settings)
    extends AbstractRedisAccessStrategy(_region, _settings)
    with EntityRegionAccessStrategy {

    override def getRegion = region

    override def get(key: Any, txTimestamp: Long): AnyRef =
        region.get(key).asInstanceOf[AnyRef]

    override def putFromLoad(key: Any,
                             value: Any,
                             txTimestamp: Long,
                             version: Any,
                             minimalPutOverride: Boolean): Boolean = {
        if (minimalPutOverride && region.contains(key)) {
            return false
        }

        region.put(key, value)
        true
    }

    override def lockItem(key: Any, version: Any): SoftLock = null

    override def unlockItem(key: Any, lock: SoftLock) {
        region.remove(key)
    }

    override def insert(key: Any, value: Any, version: Any): Boolean = false

    override def afterInsert(key: Any, value: Any, version: Any): Boolean = false

    override def update(key: Any, value: Any, currentVersion: Any, previousVersion: Any): Boolean = {
        remove(key)
        true
    }

    override def afterUpdate(key: Any,
                             value: Any,
                             currentVersion: Any,
                             previousVersion: Any,
                             lock: SoftLock): Boolean = {
        unlockItem(key, lock)
        true
    }
}

/**
 * NonStrictReadWriteRedisNatualIdRegionAccessStrategy
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2014. 2. 21. 오후 1:22
 */
class NonStrictReadWriteRedisNatualIdRegionAccessStrategy(private[this] val _region: RedisNaturalIdRegion,
                                                          private[this] val _settings: Settings)
    extends AbstractRedisAccessStrategy(_region, _settings)
    with NaturalIdRegionAccessStrategy {

    override def getRegion = region

    override def get(key: Any, txTimestamp: Long) =
        region.get(key).asInstanceOf[AnyRef]

    override def putFromLoad(key: Any,
                             value: Any,
                             txTimestamp: Long,
                             version: Any,
                             minimalPutOverride: Boolean): Boolean = {
        if (minimalPutOverride && region.contains(key)) {
            return false
        }

        region.put(key, value)
        true
    }

    override def lockItem(key: Any, version: Any): SoftLock = null

    override def unlockItem(key: Any, lock: SoftLock) {
        region.remove(key)
    }

    def insert(key: Any, value: Any): Boolean = false

    def afterInsert(key: Any, value: Any): Boolean = false

    def update(key: Any, value: Any): Boolean = {
        remove(key)
        true
    }

    def afterUpdate(key: Any, value: Any, lock: SoftLock): Boolean = {
        unlockItem(key, lock)
        true
    }
}
