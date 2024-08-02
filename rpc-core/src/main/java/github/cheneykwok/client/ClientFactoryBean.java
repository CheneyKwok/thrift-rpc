package github.cheneykwok.client;

import github.cheneykwok.RpcInvocationHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;

/**
 * RPC 客户端 FactoryBean
 *
 * @author gzc
 * @date 2024-07-29
 */
@Data
public class ClientFactoryBean implements FactoryBean<Object>, InitializingBean {

    private Logger log = LoggerFactory.getLogger(getClass());

    private String serverId;

    private Class<?> type;

    private Class<?> ifaceClass;

    private  BeanFactory beanFactory;

    @Override
    public Object getObject() {
        return getTarget();
    }

    @SuppressWarnings("unchecked")
    <T> T getTarget() {

        RpcInvocationHandler invocationHandler = new RpcInvocationHandler(serverId, ifaceClass, type);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, invocationHandler);
    }

    @Override

    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Succeed to instantiate an instance of ThriftClientFactoryBean: {}", this);
    }
}
