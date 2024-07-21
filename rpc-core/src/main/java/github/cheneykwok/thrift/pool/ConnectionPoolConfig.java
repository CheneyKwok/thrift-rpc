package github.cheneykwok.thrift.pool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;

public class ConnectionPoolConfig {

    /**
     * @see GenericKeyedObjectPool#setMaxActive
     */
    public int maxActive = GenericKeyedObjectPool.DEFAULT_MAX_ACTIVE;

    /**
     * @see GenericKeyedObjectPool#setMaxTotal
     */
    public int maxTotal = GenericKeyedObjectPool.DEFAULT_MAX_TOTAL;

    /**
     * @see GenericKeyedObjectPool#setMaxIdle
     */
    public int maxIdle = GenericKeyedObjectPool.DEFAULT_MAX_IDLE;

    /**
     * @see GenericKeyedObjectPool#setMinIdle
     */
    public int minIdle = GenericKeyedObjectPool.DEFAULT_MIN_IDLE;

    /**
     * @see GenericKeyedObjectPool#setMaxWait
     */
    public long maxWait = GenericKeyedObjectPool.DEFAULT_MAX_WAIT;

    /**
     * @see GenericKeyedObjectPool#setWhenExhaustedAction
     */
    public byte whenExhaustedAction = GenericKeyedObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION;

    /**
     * @see GenericKeyedObjectPool#setTestOnBorrow
     */
    public boolean testOnBorrow = GenericKeyedObjectPool.DEFAULT_TEST_ON_BORROW;

    /**
     * @see GenericKeyedObjectPool#setTestOnReturn
     */
    public boolean testOnReturn = GenericKeyedObjectPool.DEFAULT_TEST_ON_RETURN;

    /**
     * @see GenericKeyedObjectPool#setTestWhileIdle
     */
    public boolean testWhileIdle = GenericKeyedObjectPool.DEFAULT_TEST_WHILE_IDLE;

    /**
     * @see GenericKeyedObjectPool#setTimeBetweenEvictionRunsMillis
     */
    public long timeBetweenEvictionRunsMillis = GenericKeyedObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * @see GenericKeyedObjectPool#setNumTestsPerEvictionRun
     */
    public int numTestsPerEvictionRun =  GenericKeyedObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    /**
     * @see GenericKeyedObjectPool#setMinEvictableIdleTimeMillis
     */
    public long minEvictableIdleTimeMillis = GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;


    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
}
