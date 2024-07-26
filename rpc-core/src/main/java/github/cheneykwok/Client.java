package github.cheneykwok;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import github.cheneykwok.thrift.gen.inner.Request;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author gzc
 * @date 2024-07-26
 */
public interface Client<T extends TServiceClient> {

    Object execute(RpcRequestTemplate requestTemplate, T client);

    class Default implements Client<InnerRpcService.Client> {

        @Override
        public Object execute(RpcRequestTemplate requestTemplate, InnerRpcService.Client client) {
            MethodMetadata methodMetadata = requestTemplate.getMethodMetadata();
            Class<?> clazz = methodMetadata.getTargetType();
            Method method = methodMetadata.getMethod();
            Object[] args = methodMetadata.getArgs();

            Request request = new github.cheneykwok.thrift.gen.inner.Request();

            request.setHeader(requestTemplate.getHeader());
            request.setClassCanonicalName(clazz.getCanonicalName());
            request.setMethodName(method.getName());
            if (args != null) {
                request.setParameters(Arrays.stream(args).map(obj -> obj instanceof String ? obj.toString() : JSON.toJSONString(obj)).collect(Collectors.toList()));
                request.setParameterTypes(Arrays.stream(method.getParameterTypes()).map(Class::getCanonicalName).collect(Collectors.toList()));
            }
            try {
                github.cheneykwok.thrift.gen.inner.Response response = client.request(request);
                return response.getData();
            } catch (TException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
