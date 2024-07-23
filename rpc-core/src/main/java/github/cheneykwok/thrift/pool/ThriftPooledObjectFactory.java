package github.cheneykwok.thrift.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.lang.reflect.Constructor;

public class ThriftPooledObjectFactory extends BaseKeyedPooledObjectFactory<ConnectionKey, TServiceClient> {

    @Override
    public TServiceClient create(ConnectionKey key) throws Exception {
        TSocket socket = new TSocket(key.getServiceProperty().getHost(), key.getServiceProperty().getPort(), key.getConnectTimeout());
        TTransport transport = new TFramedTransport(socket);
        transport.open();
        TProtocol protocol = new TCompactProtocol(transport);
        Constructor<?> cons = key.getTServiceClientClass().getConstructor(TProtocol.class);
        return (TServiceClient) cons.newInstance(protocol);
    }

    @Override
    public PooledObject<TServiceClient> wrap(TServiceClient client) {
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(ConnectionKey key, PooledObject<TServiceClient> pooledObject) {
        TTransport transport = pooledObject.getObject().getInputProtocol().getTransport();
        if (transport.isOpen()) {
            transport.close();
        }
    }

    @Override
    public boolean validateObject(ConnectionKey key, PooledObject<TServiceClient> pooledObject) {
        return pooledObject.getObject().getInputProtocol().getTransport().isOpen();
    }

}
