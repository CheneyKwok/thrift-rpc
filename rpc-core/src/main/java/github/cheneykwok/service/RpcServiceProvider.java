package github.cheneykwok.service;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public Object getService(String methodKey) {
        return methodToServiceMap.get(methodKey);
    }

    public void addService(Object rpcServiceBean) {
        Class<?> clazz = rpcServiceBean.getClass();
        Method[] methods = clazz.getMethods();
        List<Class<?>> interfaces = clazz.isInterface() ? Collections.singletonList(clazz) : Arrays.asList(clazz.getInterfaces());
        for (Class<?> interfaceClass : interfaces) {
            for (Method method : methods) {
                String methodKey = keyGenerator(interfaceClass, method);
                methodToServiceMap.putIfAbsent(methodKey, rpcServiceBean);
            }
        }
    }

    public static String keyGenerator(Class<?> clazz, Method method) {
        Map<String, Object> container = new HashMap<>(3);
        // 类地址
        container.put("class", clazz.toGenericString());
        // 方法名称
        container.put("methodName", method.getName());
        Parameter[] parameters = method.getParameters();
        // 参数列表
        for (int i = 0; i < parameters.length; i++) {
            container.put(String.valueOf(i), parameters[i]);
        }
        String jsonString = JSON.toJSONString(container);
        // 做 SHA256 Hash 计算，得到一个 SHA256 摘要作为 Key
        return DigestUtils.sha256Hex(jsonString);
    }

    /**
     * 根据接口全类名、方法名、方法参数类型、返回类型生成唯一的 key。
     * @param interfaceClass 接口的 Class 对象
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型数组
     * @return 唯一的 key
     */
    public static String generateKey(Class<?> interfaceClass, String methodName, Class<?>[] parameterTypes) {
        // 获取接口的全限定名
        String interfaceClassName = interfaceClass.getCanonicalName();

        // 获取方法参数类型名称，拼接成一个字符串
        String parameterTypesString = Arrays.stream(parameterTypes)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(","));

        // 拼接字符串：接口全限定名#方法名(参数类型)
        String methodSignature = String.format("%s#%s(%s)",
                interfaceClassName, methodName, parameterTypesString);

        // 返回方法签名作为唯一的 key
        return methodSignature;
    }
}
