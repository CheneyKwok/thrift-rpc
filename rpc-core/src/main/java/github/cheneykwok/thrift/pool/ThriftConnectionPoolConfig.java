package github.cheneykwok.thrift.pool;

import lombok.Data;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

@Data
public class ThriftConnectionPoolConfig {

    /**
     * 最大等待时间
     */
    public long maxWaitTimeMills = 100;

    private int maxIdlePerKey = GenericKeyedObjectPoolConfig.DEFAULT_MAX_IDLE_PER_KEY;

    private int minIdlePerKey = GenericKeyedObjectPoolConfig.DEFAULT_MIN_IDLE_PER_KEY;

    private int maxTotalPerKey = GenericKeyedObjectPoolConfig.DEFAULT_MAX_TOTAL_PER_KEY;

    public int maxTotal = GenericKeyedObjectPoolConfig.DEFAULT_MAX_TOTAL;

    /**
     * @see org.apache.commons.pool2.impl.BaseGenericObjectPool#setTestOnBorrow
     */
    public boolean testOnBorrow = BaseObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

    /**
     * @see org.apache.commons.pool2.impl.BaseGenericObjectPool#setTestOnReturn
     */
    public boolean testOnReturn = BaseObjectPoolConfig.DEFAULT_TEST_ON_RETURN;

    /**
     * @see org.apache.commons.pool2.impl.BaseGenericObjectPool#setTestWhileIdle
     */
    public boolean testWhileIdle = BaseObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;

    /**
     * 空闲对象逐出线程运行之间休眠的毫秒数。
     */
    public long timeBetweenEvictionRunsMillis = 5000;

}
