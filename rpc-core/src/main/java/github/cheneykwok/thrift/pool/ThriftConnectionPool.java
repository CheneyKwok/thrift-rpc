package github.cheneykwok.thrift.pool;

import org.apache.commons.pool.BaseKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftConnectionPool extends BaseKeyedObjectPool<ConnectionKey, TServiceClient> {

    private static final Logger logger = LoggerFactory.getLogger(ThriftConnectionPool.class);

    private final GenericKeyedObjectPool<ConnectionKey, TServiceClient> connectionPool;

    public ThriftConnectionPool(ConnectionPoolConfig poolConfig) {
        this.connectionPool = new GenericKeyedObjectPool<>(new ThriftPooledObjectFactory());
        this.connectionPool.setMaxActive(poolConfig.getMaxActive());
        this.connectionPool.setMaxTotal(poolConfig.getMaxTotal());
        this.connectionPool.setMaxIdle(poolConfig.getMaxIdle());
        this.connectionPool.setMinIdle(poolConfig.getMinIdle());
        this.connectionPool.setMaxWait(poolConfig.getMaxWait());
        this.connectionPool.setWhenExhaustedAction(poolConfig.getWhenExhaustedAction());
        this.connectionPool.setTestOnBorrow(poolConfig.isTestOnBorrow());
        this.connectionPool.setTestOnReturn(poolConfig.isTestOnReturn());
        this.connectionPool.setTestWhileIdle(poolConfig.isTestWhileIdle());
        this.connectionPool.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRunsMillis());
        this.connectionPool.setNumTestsPerEvictionRun(poolConfig.getNumTestsPerEvictionRun());
        this.connectionPool.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
    }

    @Override
    public TServiceClient borrowObject(ConnectionKey key) {
        TServiceClient client;
        try {
            client = this.connectionPool.borrowObject(key);
        } catch (Exception e) {
            logger.error("borrow client with key={} failed", key, e);
            return null;
        }
        return client;
    }

    @Override
    public void returnObject(ConnectionKey key, TServiceClient client) throws Exception {
        this.connectionPool.returnObject(key, client);

    }

    @Override
    public void invalidateObject(ConnectionKey key, TServiceClient client) throws Exception {
        this.connectionPool.invalidateObject(key, client);
    }

    public void shutdown() {
        try {
            this.connectionPool.close();
        } catch (Exception e) {
            logger.error("thrift connection pool shutdown error", e);
        }
    }
}
