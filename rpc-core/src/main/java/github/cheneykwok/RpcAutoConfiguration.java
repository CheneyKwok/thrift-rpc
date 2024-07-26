package github.cheneykwok;

import github.cheneykwok.service.RpcApplicationListener;
import github.cheneykwok.service.RpcServiceManager;
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
    public RpcServiceManager rpcServiceManager() {
        return new RpcServiceManager();
    }

    @Bean
    public RpcServiceImpl rpcServiceImpl(RpcServiceManager rpcServiceManager) {
        return new RpcServiceImpl(rpcServiceManager);
    }

    @Bean
    public RpcApplicationListener rpcApplicationListener(RpcServiceManager rpcServiceManager, RpcServiceImpl rpcServiceImpl) {
        return new RpcApplicationListener(rpcServiceManager, rpcServiceImpl);
    }
}
