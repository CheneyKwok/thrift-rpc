package github.cheneykwok.client;

import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.thrift.pool.ThriftClientPool;

/**
 * 客户端上下文
 *
 * @author gzc
 * @date 2024-07-29
 */
public class ClientContext {

    private ClientProperties clientProperties;

    private ThriftClientPool clientPool;


    private ClientContext() {
    }

    public static ClientContext context(ClientProperties clientProperties, ThriftClientPool clientPool) {
        context().clientProperties = clientProperties;
        context().clientPool = clientPool;
        return context();
    }

    private static final class ContextHolder {
        static final ClientContext context = new ClientContext();
    }

    public static ClientContext context() {
        return ContextHolder.context;
    }

    public ClientProperties getClientProperties() {
        return clientProperties;
    }

    public ThriftClientPool getClientPool() {
        return clientPool;
    }
}
