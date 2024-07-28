package github.cheneykwok;

public interface RpcRequestInterceptor {

    void apply(RpcRequestTemplate template);
}
