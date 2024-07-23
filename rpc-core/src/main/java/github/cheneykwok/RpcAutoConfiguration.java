package github.cheneykwok;

import github.cheneykwok.service.RpcApplicationListener;
import github.cheneykwok.service.RpcServiceProvider;
import github.cheneykwok.thrift.impl.RpcServiceImpl;
import github.cheneykwok.thrift.pool.ThriftConnectionPool;
import github.cheneykwok.thrift.pool.ThriftConnectionPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class RpcAutoConfiguration {

    @Bean
    public ThriftConnectionPoolConfig thriftConnectionPoolConfig() {
        return new ThriftConnectionPoolConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public ThriftConnectionPool thriftConnectionPool(ThriftConnectionPoolConfig thriftConnectionPoolConfig) {
        return new ThriftConnectionPool(thriftConnectionPoolConfig);
    }

    @Bean
    public RpcServiceProvider rpcServiceProvider() {
        return new RpcServiceProvider();
    }

    @Bean
    public RpcServiceImpl rpcServiceImpl(RpcServiceProvider rpcServiceProvider) {
        return new RpcServiceImpl(rpcServiceProvider);
    }

    @Bean
    public RpcApplicationListener rpcApplicationListener(RpcServiceProvider rpcServiceProvider, RpcServiceImpl rpcServiceImpl) {
        return new RpcApplicationListener(rpcServiceProvider, rpcServiceImpl);
    }
}
