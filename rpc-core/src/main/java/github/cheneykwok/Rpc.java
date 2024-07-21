package github.cheneykwok;

import java.lang.reflect.Proxy;

public class Rpc {


    public static Rpc build() {
        return new Rpc();
    }

    public <T> T target(Target<T> target) {
        return newInstance(target);
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(Target<T> target) {
        RpcInvocationHandler handler = new RpcInvocationHandler(target, null);
        return (T) Proxy.newProxyInstance(target.getType().getClassLoader(), new Class[]{target.getType()}, handler);
    }

}
