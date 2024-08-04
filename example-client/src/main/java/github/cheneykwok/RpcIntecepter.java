package github.cheneykwok;

import org.springframework.stereotype.Component;

@Component
public class RpcIntecepter implements RpcRequestInterceptor{


    @Override
    public void apply(RpcRequestTemplate template) {
        template.getHeader().putIfAbsent("traceId", "213213aeadaw");
    }
}
