package github.cheneykwok.client.pool;

import github.cheneykwok.client.ServiceKey;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.TServiceClient;

/**
 * Thrift Client 对象池
 *
 * @author gzc
 * @date 2024-07-26
 */
public class ThriftClientPool extends GenericKeyedObjectPool<ServiceKey, TServiceClient> {

    public ThriftClientPool(KeyedPooledObjectFactory<ServiceKey, TServiceClient> factory,
                            GenericKeyedObjectPoolConfig<TServiceClient> config) {

        super(factory, config);
    }

    @Override
    public TServiceClient borrowObject(ServiceKey serviceKey) throws Exception {
        return super.borrowObject(serviceKey);
    }

    @Override
    public void returnObject(ServiceKey serviceKey, TServiceClient client) {
        if (serviceKey != null && client != null) {
            super.returnObject(serviceKey, client);
        }
    }



}
