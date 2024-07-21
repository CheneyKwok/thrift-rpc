package github.cheneykwok.thrift.impl;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.service.RpcServiceProvider;
import github.cheneykwok.thrift.gen.Request;
import github.cheneykwok.thrift.gen.Response;
import github.cheneykwok.thrift.gen.RpcService;
import org.apache.thrift.TException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

public class RpcServiceImpl implements RpcService.Iface {

    private final RpcServiceProvider rpcServiceProvider;

    public RpcServiceImpl(RpcServiceProvider rpcServiceProvider) {
        this.rpcServiceProvider = rpcServiceProvider;
    }

    @Override
    public Response request(Request request) throws TException {
        String methodName = request.getMethodName();
        List<String> parameters = request.getParameters();
        List<String> parameterTypes = request.getParameterTypes();
        Object[] args = null;
        Class[] types = null;
        Object result = null;
        try {
            if (!CollectionUtils.isEmpty(parameters)) {
                // 将 string 转换为 Object
                args = new Object[parameters.size()];
                for (int i = 0; i < parameters.size(); i++) {
                    args[i] = parameters.get(i);
                }
            }
            if (!CollectionUtils.isEmpty(parameterTypes)) {
                // 将 string 转换为 Class
                types = new Class[parameterTypes.size()];
                for (int i = 0; i < parameterTypes.size(); i++) {
                    types[i] = Class.forName(parameterTypes.get(i));
                }
            }
            Object service = rpcServiceProvider.getService(methodName);
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
