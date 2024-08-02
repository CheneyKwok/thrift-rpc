package github.cheneykwok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RpcIntecepter implements RpcRequestInterceptor{


    @Value("${user}")
    private int time;

    @Override
    public void apply(RpcRequestTemplate template) {
        template.getHeader().putIfAbsent("traceId", "213213aeadaw");
    }
}
