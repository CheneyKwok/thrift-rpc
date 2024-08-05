package github.cheneykwok.client;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.MappingMethod;
import github.cheneykwok.MappingRegistry;
import github.cheneykwok.client.pool.ThriftClientPool;
import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.thrift.gen.inner.InnerRequest;
import github.cheneykwok.thrift.gen.inner.InnerResponse;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class RpcInvocationHandler implements InvocationHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Class<?> target;

    private final Class<?> ifaceClass;

    private final String serverId;

    private final String address;

    private final MappingRegistry mappingRegistry;

    private final ClientProperties clientProperties;

    private final ThriftClientPool clientPool;


    public RpcInvocationHandler(Class<?> target, Class<?> ifaceClass, String serverId, String address,
                                MappingRegistry mappingRegistry, ClientProperties clientProperties, ThriftClientPool clientPool) {
        this.target = target;
        this.ifaceClass = ifaceClass;
        this.serverId = serverId;
        this.address = address;
        this.mappingRegistry = mappingRegistry;
        this.clientProperties = clientProperties;
        this.clientPool = clientPool;
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
                MappingMethod mapping = mappingRegistry.getMapping(method);
                String path = mapping.getPath();

                InnerRequest request = new InnerRequest();
                request.setPath(path);
                request.setArg(JSON.toJSONString(args));
                InnerResponse response = innerRpcClient.request(request);
                if (!response.isSuccess) {
                    log.error("request failed, path: {}, response: {}", path, response);
                    return null;
                }
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
