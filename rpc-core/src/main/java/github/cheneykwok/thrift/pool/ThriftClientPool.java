package github.cheneykwok.thrift.pool;

import github.cheneykwok.client.ServiceKey;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftClientPool extends GenericKeyedObjectPool<ServiceKey, TServiceClient> {

    private static final Logger logger = LoggerFactory.getLogger(ThriftClientPool.class);

    public ThriftClientPool(KeyedPooledObjectFactory<ServiceKey, TServiceClient> factory, GenericKeyedObjectPoolConfig<TServiceClient> config) {
        super(factory, config);
    }

    @Override
    public TServiceClient borrowObject(ServiceKey serviceKey) throws Exception {
        return super.borrowObject(serviceKey);
    }

    @Override
    public void returnObject(ServiceKey serviceKey, TServiceClient client) {
        if (client != null) {
            super.returnObject(serviceKey, client);
        }
    }



}
