package github.cheneykwok;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.client.ClientContext;
import github.cheneykwok.client.ServiceKey;
import github.cheneykwok.client.pool.ThriftClientPool;
import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.thrift.gen.inner.InnerRequest;
import github.cheneykwok.thrift.gen.inner.InnerResponse;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import org.apache.thrift.TServiceClient;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class RpcInvocationHandler implements InvocationHandler {

    private final Class<?> target;

    private final Class<?> ifaceClass;

    private final String serverId;

    private final String address;

    private final BeanFactory beanFactory;

    private ClientProperties clientProperties;

    private ThriftClientPool clientPool;

    private RpcMappingHandler mappingHandler;

    public RpcInvocationHandler(Class<?> target, Class<?> ifaceClass, String serverId, String address, BeanFactory beanFactory) {
        this.target = target;
        this.ifaceClass = ifaceClass;
        this.serverId = serverId;
        this.address = address;
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
            case "hashCode":
                return hashCode();
            case "toString":
                return toString();
        }
        if (clientProperties == null) {
            clientProperties = ClientContext.context().getClientProperties();
        }
        if (clientPool == null) {
            clientPool = ClientContext.context().getClientPool();
        }
        if (mappingHandler == null) {
            mappingHandler = beanFactory.getBean(RpcMappingHandler.class);
        }

        String address = this.address;
        if (!StringUtils.hasText(address)) {
            Map<String, String> serverAddrList = clientProperties.getServerAddrList();
            address = serverAddrList.get(serverId);
        }
        if (!StringUtils.hasText(address)) {
            throw new RuntimeException("Invalid server address");
        }
        String[] arr = address.split(":");
        ServiceKey serviceKey = ServiceKey
                .builder()
                .host(arr[0])
                .port(Integer.parseInt(arr[1]))
                .connectTimeout(clientProperties.getConnectTimeout())
                .serviceInterfaceClass(ifaceClass)
                .build();
        Object result = null;
        TServiceClient client = null;
        try {
            client = clientPool.borrowObject(serviceKey);
            if (client instanceof InnerRpcService.Client innerRpcClient) {
                MappingMethod mapping = mappingHandler.getMethodMapping(method);
                String path = mapping.getPath();

                InnerRequest request = new InnerRequest();
                request.setPath(path);
                request.setArg(JSON.toJSONString(args));
                InnerResponse response = innerRpcClient.request(request);
                Class<?> returnType = method.getReturnType();
                result = JSON.parseObject(response.getData(), returnType);
            } else {
                return ReflectionUtils.invokeMethod(method, client, args);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            clientPool.returnObject(serviceKey, client);
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
