package github.cheneykwok;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.thrift.ServiceProperty;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import github.cheneykwok.thrift.gen.inner.Request;
import github.cheneykwok.thrift.gen.inner.Response;
import github.cheneykwok.thrift.pool.ConnectionKey;
import github.cheneykwok.thrift.pool.ThriftConnectionPool;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

class RpcInvocationHandler implements InvocationHandler {

    private final Target target;
    private final BeanFactory beanFactory;

    RpcInvocationHandler(Target target, BeanFactory beanFactory) {
        this.target = target;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "equals":
                try {
                    Object otherHandler =
                            args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                    return equals(otherHandler);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            case "hashCode": return hashCode();
            case "toString": return toString();
        }
        ConnectionKey connectionKey = new ConnectionKey();
        connectionKey.setConnectTimeout(10000);
        connectionKey.setServiceProperty(new ServiceProperty("user", "localhost", 7777));
        connectionKey.setTServiceClientClass(InnerRpcService.Client.class);
        Object result = null;
        ThriftConnectionPool connectionPool = null;
        InnerRpcService.Client client = null;
        try {
            connectionPool = beanFactory.getBean(ThriftConnectionPool.class);
            client = (InnerRpcService.Client) connectionPool.borrowObject(connectionKey);
            Class<?> clazz = target.getType();
            Request request = new github.cheneykwok.thrift.gen.inner.Request();
            request.setClassCanonicalName(clazz.getCanonicalName());
            request.setMethodName(method.getName());
            if (args != null) {
                request.setParameters(Arrays.stream(args).map(obj -> obj instanceof String ? obj.toString() : JSON.toJSONString(obj)).collect(Collectors.toList()));
                request.setParameterTypes(Arrays.stream(method.getParameterTypes()).map(Class::getCanonicalName).collect(Collectors.toList()));
            }
            Response response = client.request(request);
            Class<?> returnType = method.getReturnType();
            result = JSON.parseObject(response.getData(), returnType);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connectionPool != null && client != null) {
                connectionPool.returnObject(connectionKey, client);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RpcInvocationHandler) {
            RpcInvocationHandler other = (RpcInvocationHandler) obj;
            return target.equals(other.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
