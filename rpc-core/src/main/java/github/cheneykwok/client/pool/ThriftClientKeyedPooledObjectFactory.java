package github.cheneykwok.client.pool;

import github.cheneykwok.ThriftConstant;
import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.client.ServiceKey;
import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.server.properties.TServerModel;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.PooledSoftReference;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;

/**
 * Thrift Client 对象池工厂
 *
 * @author gzc
 * @date 2024-07-26
 */
public class ThriftClientKeyedPooledObjectFactory extends BaseKeyedPooledObjectFactory<ServiceKey, TServiceClient> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final ClientProperties properties;

    public ThriftClientKeyedPooledObjectFactory(ClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public TServiceClient create(ServiceKey serviceKey) throws Exception {
        String host = serviceKey.getHost();
        int port = serviceKey.getPort();
        Class<?> interfaceClass = serviceKey.getServiceInterfaceClass();
        TServiceClient thriftClient;
        String typeName = interfaceClass.getName();
        if (typeName.endsWith(ThriftConstant.THRIFT_IFACE)) {
            String clientClsName = typeName.substring(0, typeName.indexOf(ThriftConstant.THRIFT_IFACE)) + ThriftConstant.THRIFT_CLIENT;
            Class<?> clazz = Class.forName(clientClsName);
            Constructor<?> constructor = clazz.getConstructor(TProtocol.class);
            try {
                TTransport tTransport = getTTransport(serviceKey, properties);
                TProtocol tprotocol = ThriftTProtocolFactory.getProtocol(properties.getProtocol(), tTransport);
                TMultiplexedProtocol protocol = new TMultiplexedProtocol(tprotocol, typeName);
                thriftClient = (TServiceClient) constructor.newInstance(protocol);
                tTransport.open();
                log.info("create a new client for server (" + host + ":" + port + ")");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("Fail to create client for server (" + host + ":" + port + ")" + e.getMessage(), e);
            }
        } else {
            throw new IllegalStateException("The interface class(" + interfaceClass.getName() + ") is not a thrift interface class");
        }
        return thriftClient;
    }

    private TTransport getTTransport(ServiceKey serviceKey, ClientProperties properties) throws TTransportException {
        String host = serviceKey.getHost();
        int port = serviceKey.getPort();
        int connectTimeout = serviceKey.getConnectTimeout();
        TServerModel serverModel = properties.getServerModel();
        switch (serverModel) {
            case SIMPLE, THREAD_POOL -> {
                return new TSocket(host, port, connectTimeout);
            }
            case NON_BLOCKING, HS_HA, THREADED_SELECTOR -> {
                return new TFramedTransport(new TSocket(host, port, connectTimeout));
            }
            default -> throw new IllegalStateException("Unsupported server model: " + serverModel);
        }
    }


    @Override
    public PooledObject<TServiceClient> wrap(TServiceClient client) {
        SoftReference<TServiceClient> softReference = new SoftReference<>(client);
        return new PooledSoftReference<>(softReference);
    }

    @Override
    public void destroyObject(ServiceKey key, PooledObject<TServiceClient> pooledObject) {
        PooledSoftReference<TServiceClient> softReference = (PooledSoftReference<TServiceClient>) pooledObject;
        TServiceClient client = softReference.getObject();
        if (client.getInputProtocol().getTransport().isOpen()) {
            client.getInputProtocol().getTransport().close();
        }
        if (client.getOutputProtocol().getTransport().isOpen()) {
            client.getOutputProtocol().getTransport().close();
        }
    }

    @Override
    public boolean validateObject(ServiceKey key, PooledObject<TServiceClient> pooledObject) {
        PooledSoftReference<TServiceClient> softReference = (PooledSoftReference<TServiceClient>) pooledObject;
        TServiceClient client = softReference.getObject();
        return client.getInputProtocol().getTransport().isOpen() && client.getOutputProtocol().getTransport().isOpen();
    }

}
