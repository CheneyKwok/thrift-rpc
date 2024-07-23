package github.cheneykwok.thrift.pool;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ThriftConnectionPool implements IConnectionPool<ConnectionKey, TServiceClient> {

    private static final Logger logger = LoggerFactory.getLogger(ThriftConnectionPool.class);

    private final GenericKeyedObjectPool<ConnectionKey, TServiceClient> connectionPool;

    public ThriftConnectionPool(ThriftConnectionPoolConfig poolConfig) {
        this.connectionPool = new GenericKeyedObjectPool<>(new ThriftPooledObjectFactory());
        this.connectionPool.setMaxWait( Duration.ofMillis(poolConfig.getMaxWaitTimeMills()));
        this.connectionPool.setMaxIdlePerKey(poolConfig.getMaxIdlePerKey());
        this.connectionPool.setMinIdlePerKey(poolConfig.getMinIdlePerKey());
        this.connectionPool.setMaxTotalPerKey(poolConfig.getMaxTotalPerKey());
        this.connectionPool.setMaxTotal(poolConfig.getMaxTotal());
        this.connectionPool.setTestOnBorrow(poolConfig.isTestOnBorrow());
        this.connectionPool.setTestOnReturn(poolConfig.isTestOnReturn());
        this.connectionPool.setTestWhileIdle(poolConfig.isTestWhileIdle());
        this.connectionPool.setDurationBetweenEvictionRuns(Duration.ofMillis(poolConfig.getTimeBetweenEvictionRunsMillis()));
    }

    @Override
    public TServiceClient borrowObject(ConnectionKey key) {
        TServiceClient client;
        try {
            client = this.connectionPool.borrowObject(key);
        } catch (Exception e) {
            logger.error("borrow client with key={} error", key, e);
            return null;
        }
        return client;
    }

    @Override
    public void returnObject(ConnectionKey key, TServiceClient client) {
        this.connectionPool.returnObject(key, client);

    }

    @Override
    public void invalidateObject(ConnectionKey key, TServiceClient client){
        try {
            this.connectionPool.invalidateObject(key, client);
        } catch (Exception e) {
            logger.error("invalidate client with key={} error", key, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            this.connectionPool.close();
        } catch (Exception e) {
            logger.error("thrift connection pool shutdown error", e);
        }
    }
}
