package github.cheneykwok.client;

import github.cheneykwok.RpcMappingHandler;
import github.cheneykwok.client.properties.ClientPoolProperties;
import github.cheneykwok.client.properties.ClientProperties;
import github.cheneykwok.server.RpcApplicationListener;
import github.cheneykwok.server.RpcServiceManager;
import github.cheneykwok.thrift.impl.RpcServiceImpl;
import github.cheneykwok.thrift.pool.ThriftClientKeyedPooledObjectFactory;
import github.cheneykwok.thrift.pool.ThriftClientPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.TServiceClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@AutoConfiguration
@EnableConfigurationProperties({ClientProperties.class})
public class RpcClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GenericKeyedObjectPoolConfig<TServiceClient> keyedObjectPoolConfig(ClientProperties properties) {
        ClientPoolProperties poolProperties = properties.getPool();
        GenericKeyedObjectPoolConfig<TServiceClient> config = new GenericKeyedObjectPoolConfig<>();
        config.setMaxWait(Duration.ofMillis(poolProperties.getMaxWaitTimeMills()));
        config.setMaxTotal(poolProperties.getMaxTotal());
        config.setMaxIdlePerKey(poolProperties.getMaxIdlePerKey());
        config.setMinIdlePerKey(poolProperties.getMinIdlePerKey());
        config.setMaxTotalPerKey(poolProperties.getMaxTotalPerKey());
        config.setTestOnCreate(poolProperties.isTestOnCreate());
        config.setTestOnBorrow(poolProperties.isTestOnBorrow());
        config.setTestOnReturn(poolProperties.isTestOnReturn());
        config.setJmxEnabled(false);
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public ThriftClientKeyedPooledObjectFactory thriftClientKeyedPooledObjectFactory(ClientProperties properties) {
        return new ThriftClientKeyedPooledObjectFactory(properties);
    }


    @Bean
    @ConditionalOnMissingBean
    public ThriftClientPool thriftTransportPool(
            ThriftClientKeyedPooledObjectFactory factory, GenericKeyedObjectPoolConfig<TServiceClient> config) {
        return new ThriftClientPool(factory, config);
    }

    @Bean
    public RpcServiceManager rpcServiceManager() {
        return new RpcServiceManager();
    }

    @Bean
    public RpcServiceImpl rpcServiceImpl(RpcMappingHandler rpcMappingHandler) {
        return new RpcServiceImpl(rpcMappingHandler);
    }

    @Bean
    public RpcApplicationListener rpcApplicationListener(RpcServiceManager rpcServiceManager, RpcServiceImpl rpcServiceImpl) {
        return new RpcApplicationListener(rpcServiceManager, rpcServiceImpl);
    }

    @Bean
    public ClientContext clientContext(ClientProperties clientProperties, ThriftClientPool clientPool) {
        return ClientContext.context(clientProperties, clientPool);
    }

}
