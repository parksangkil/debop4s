package com.github.debop4s.core.stests.pool

import org.apache.commons.pool2.impl.GenericObjectPoolConfig


/**
 * com.github.debop4s.core.tests.pool.ObjectPoolConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 2013. 12. 11. 오후 4:43
 */
class ObjectPoolConfig extends GenericObjectPoolConfig {

    setMaxTotal(100)
}
