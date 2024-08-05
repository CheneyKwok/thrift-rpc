package github.cheneykwok.client;

import github.cheneykwok.MappingRegistry;
import github.cheneykwok.client.pool.ThriftClientPool;
import github.cheneykwok.client.properties.ClientProperties;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;

import static org.springframework.util.Assert.notNull;

/**
 * RPC 客户端 FactoryBean
 *
 * @author gzc
 * @date 2024-07-29
 */
@Data
public class ClientFactoryBean implements FactoryBean<Object>, InitializingBean {

    private Logger log = LoggerFactory.getLogger(getClass());

    private Class<?> type;

    private Class<?> ifaceClass;

    private String serverId;

    private String address;

    private MappingRegistry mappingRegistry;

    private ClientProperties clientProperties;

    private ThriftClientPool clientPool;

    @Override
    public Object getObject() {
        return getTarget();
    }

    @SuppressWarnings("unchecked")
    <T> T getTarget() {
        RpcInvocationHandler invocationHandler = new RpcInvocationHandler(type, ifaceClass, serverId, address,
                mappingRegistry, clientProperties, clientPool);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, invocationHandler);
    }

    @Override

    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() {
        notNull(this.type, "Property 'type' is required");
        notNull(this.ifaceClass, "Property 'ifaceClass' is required");
    }
}
