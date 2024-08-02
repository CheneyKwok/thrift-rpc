package github.cheneykwok;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.client.ClientContext;
import github.cheneykwok.client.ServiceKey;
import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.thrift.gen.inner.InnerRequest;
import github.cheneykwok.thrift.gen.inner.InnerResponse;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import github.cheneykwok.thrift.pool.ThriftClientPool;
import org.apache.thrift.TServiceClient;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class RpcInvocationHandler implements InvocationHandler {

    private final String serverId;

    private final Class<?> target;

    private final Class<?> ifaceClass;

    private ClientProperties clientProperties;

    private ThriftClientPool clientPool;

    public RpcInvocationHandler(String serverId, Class<?> ifaceClass, Class<?> target) {
        this.serverId = serverId;
        this.ifaceClass = ifaceClass;
        this.target = target;
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

        Map<String, String> serverAddrList = clientProperties.getServerAddrList();
        String serverAddr = serverAddrList.get(serverId);
        if (serverAddr == null) {
            throw new RuntimeException("No server address found for serverId: " + serverId);
        }
        String[] address = serverAddr.split(":");
        ServiceKey serviceKey = ServiceKey
                .builder()
                .host(address[0])
                .port(Integer.parseInt(address[1]))
                .connectTimeout(clientProperties.getConnectTimeout())
                .serviceInterfaceClass(ifaceClass)
                .build();
        Object result = null;
        TServiceClient client = null;
        try {
            client = clientPool.borrowObject(serviceKey);
            if (client instanceof InnerRpcService.Client innerRpcClient) {
                String path = getPath(proxy, method);

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

    private String getPath(Object proxy, Method method) {
        RpcMapping methodMapping = method.getAnnotation(RpcMapping.class);
        if (methodMapping == null || !StringUtils.hasText(methodMapping.value())) {
            throw new IllegalStateException("@RpcMapping value must not be empty, method: " + method.toGenericString());
        }
        String path = methodMapping.value();
        RpcMapping classMapping = AnnotationUtils.findAnnotation(proxy.getClass(), RpcMapping.class);
        if (classMapping != null && StringUtils.hasText(classMapping.value())) {
            path = classMapping.value() + path;
        }
        return path;
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
