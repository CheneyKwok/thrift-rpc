package github.cheneykwok.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServiceProvider {

    private final Logger log = LoggerFactory.getLogger(RpcServiceProvider.class);

    private final Map<String, Object> serviceMap;

    private final Map<String, Object> methodToServiceMap;

    private final Set<String> registeredService;

    public RpcServiceProvider() {
        serviceMap = new ConcurrentHashMap<>();
        methodToServiceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
    }

    public Object getService(String methodName) {
        return methodToServiceMap.get(methodName);
    }

    public void addService(Object rpcServiceBean) {
        Class<?> clazz = rpcServiceBean.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            methodToServiceMap.putIfAbsent(method.getName(), rpcServiceBean);
        }
    }
}
