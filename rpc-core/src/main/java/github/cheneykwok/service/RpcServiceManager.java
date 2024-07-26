package github.cheneykwok.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RpcServiceManager {

    private final Logger log = LoggerFactory.getLogger(RpcServiceManager.class);

    private final Set<String> ignoredMethods = Set.of("toString", "hashCode", "equals", "wait", "notify", "notifyAll");

    private final Map<String, Object> serviceMap;

    private final Map<String, Object> methodToServiceMap;

    private final Set<String> registeredService;

    public RpcServiceManager() {
        serviceMap = new ConcurrentHashMap<>();
        methodToServiceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
    }

    public Object getService(String methodKey) {
        return methodToServiceMap.get(methodKey);
    }

    public void addService(Object rpcServiceBean) {
        Class<?> clazz = rpcServiceBean.getClass();
        Method[] methods = clazz.getMethods();
        List<Class<?>> interfaces = clazz.isInterface() ? Collections.singletonList(clazz) : Arrays.asList(clazz.getInterfaces());
        for (Class<?> interfaceClass : interfaces) {
            for (Method method : methods) {
                if (ignoredMethods.contains(method.getName())) {
                    continue;
                }
                String methodKey = generateMethodKey(interfaceClass, method.getName(), method.getParameterTypes());
                methodToServiceMap.putIfAbsent(methodKey, rpcServiceBean);
            }
        }
    }

    /**
     * 根据接口全类名、方法名、方法参数类型生成唯一的 key。
     * @param interfaceClass 接口的 Class 对象
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型数组
     * @return 唯一的 key
     */
    public static String generateMethodKey(Class<?> interfaceClass, String methodName, Class<?>[] parameterTypes) {
        // 获取接口的全限定名
        String interfaceClassName = interfaceClass.getCanonicalName();

        // 获取方法参数类型名称，拼接成一个字符串
        List<String> parameterTypeList = Arrays.stream(parameterTypes)
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());

        return generateMethodKey(interfaceClassName, methodName, parameterTypeList);
    }

    /**
     * 根据接口全类名、方法名、方法参数类型生成唯一的 key。
     * @param interfaceClassName 接口的全限定名
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型的全限定名数组
     * @return 唯一的 key
     */
    public static String generateMethodKey(String interfaceClassName, String methodName, List<String> parameterTypes) {

        // 将方法参数类型名称，拼接成一个字符串
        String parameterTypesString = String.join(",", parameterTypes);

        // 拼接字符串：接口全限定名#方法名(参数类型)

        // 返回方法签名作为唯一的 key
        return String.format("%s#%s(%s)", interfaceClassName, methodName, parameterTypesString);
    }
}
