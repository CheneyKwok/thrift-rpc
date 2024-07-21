package github.cheneykwok;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class RpcClientFactoryBean implements FactoryBean<Object> {

    private Class<?> type;

    private String name;

    private int readTimeoutMillis;

    private int connectTimeoutMillis;

    private BeanFactory beanFactory;


    @Override
    public Object getObject() {
        return newInstance(new Target<>(type), beanFactory);
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(Target<T> target, BeanFactory beanFactory) {
        RpcInvocationHandler handler = new RpcInvocationHandler(target, beanFactory);
        return (T) Proxy.newProxyInstance(target.getType().getClassLoader(), new Class[]{target.getType()}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public void setReadTimeoutMillis(int readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
    }

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(int connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
