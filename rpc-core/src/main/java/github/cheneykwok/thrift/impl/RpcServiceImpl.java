package github.cheneykwok.thrift.impl;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.MappingMethod;
import github.cheneykwok.RpcMappingHandler;
import github.cheneykwok.thrift.gen.inner.InnerRequest;
import github.cheneykwok.thrift.gen.inner.InnerResponse;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import org.apache.thrift.TException;

import java.lang.reflect.Method;
import java.util.List;

public class RpcServiceImpl implements InnerRpcService.Iface {

    private final RpcMappingHandler mappingHandler;

    public RpcServiceImpl(RpcMappingHandler mappingHandler) {
        this.mappingHandler = mappingHandler;
    }

    @Override
    public InnerResponse request(InnerRequest request) throws TException {
        String path = request.getPath();
        String arg = request.getArg();
        Object[] args = null;
        Object result;
        try {
            MappingMethod mappingMethod = mappingHandler.getMethodMapping(path);
            if (mappingMethod == null) {
                return new InnerResponse(404, "not found", false);
            }
            if (arg != null) {
                List<String> requestArgs = JSON.parseArray(arg, String.class);
                Method method = mappingMethod.getMethod();
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != requestArgs.size()) {
                    return new InnerResponse(400, "参数个数不匹配", false);
                }
                args = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    args[i] = JSON.to(parameterTypes[i], requestArgs.get(i));
                }
            }
            result = mappingMethod.doInvoke(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        InnerResponse response = new InnerResponse(200, "success", true);
        response.setData(JSON.toJSONString(result));

        return response;
    }


}
