package github.cheneykwok.thrift.pool;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.lang.reflect.Constructor;

public class ThriftPooledObjectFactory extends BaseKeyedPoolableObjectFactory<ConnectionKey, TServiceClient> {

    @Override
    public TServiceClient makeObject(ConnectionKey key) throws Exception {
        return create(key);
    }

    public TServiceClient create(ConnectionKey key) throws Exception {
        TSocket socket = new TSocket(key.getServiceProperty().getHost(), key.getServiceProperty().getPort(), key.getConnectTimeout());
        TTransport transport = new TFramedTransport(socket);
        transport.open();
        TProtocol protocol = new TCompactProtocol(transport);
        Constructor<?> cons = key.gettServiceClientClass().getConstructor(TProtocol.class);
        return (TServiceClient) cons.newInstance(protocol);
    }

    @Override
    public void destroyObject(ConnectionKey key, TServiceClient client) throws Exception {
        TTransport transport = client.getInputProtocol().getTransport();
        if (transport.isOpen()) {
            transport.close();
        }
    }

    @Override
    public boolean validateObject(ConnectionKey key, TServiceClient client) {
        return client.getInputProtocol().getTransport().isOpen();
    }
}
