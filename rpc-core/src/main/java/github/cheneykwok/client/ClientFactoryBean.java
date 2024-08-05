package github.cheneykwok.client;

import github.cheneykwok.RpcInvocationHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
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

    private BeanFactory beanFactory;

    private Class<?> type;

    private Class<?> ifaceClass;

    private String serverId;

    private String address;

    @Override
    public Object getObject() {
        return getTarget();
    }

    @SuppressWarnings("unchecked")
    <T> T getTarget() {
        RpcInvocationHandler invocationHandler = new RpcInvocationHandler(type, ifaceClass, serverId, address, beanFactory);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, invocationHandler);
    }

    @Override

    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() {
        notNull(this.beanFactory, "Property 'beanFactory' is required");
        notNull(this.type, "Property 'type' is required");
        notNull(this.ifaceClass, "Property 'ifaceClass' is required");
    }
}
