package github.cheneykwok.thrift.impl;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.service.RpcServiceManager;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import github.cheneykwok.thrift.gen.inner.Request;
import github.cheneykwok.thrift.gen.inner.Response;
import org.apache.thrift.TException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

public class RpcServiceImpl implements InnerRpcService.Iface {

    private final RpcServiceManager rpcServiceManager;

    public RpcServiceImpl(RpcServiceManager rpcServiceManager) {
        this.rpcServiceManager = rpcServiceManager;
    }

    @Override
    public Response request(Request request) throws TException {
        String classCanonicalName = request.getClassCanonicalName();
        String methodName = request.getMethodName();
        List<String> parameters = request.getParameters();
        List<String> parameterTypes = request.getParameterTypes();
        Object[] args = null;
        Class[] types = null;
        Object result = null;
        try {
            if (!CollectionUtils.isEmpty(parameterTypes)) {
                // 将 string 转换为 Class
                types = new Class[parameterTypes.size()];
                for (int i = 0; i < parameterTypes.size(); i++) {
                    // todo 类加载做缓存优化
                    types[i] = Class.forName(parameterTypes.get(i));
                }
                if (!CollectionUtils.isEmpty(parameters)) {
                    // 将 string 转换为 Object
                    args = new Object[parameters.size()];
                    for (int i = 0; i < parameters.size(); i++) {
                        Class<?> typeClass = types[i];
                        args[i] = JSON.to(typeClass, parameters.get(i));
                    }
                }
            }
            String methodKey = RpcServiceManager.generateMethodKey(classCanonicalName, methodName, parameterTypes);
            Object service = rpcServiceManager.getService(methodKey);
            Method method = service.getClass().getMethod(methodName, types);
            result = method.invoke(service, args);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Response response = new Response(200, "success", true);
        response.setData(JSON.toJSONString(result));

        return response;
    }


}
