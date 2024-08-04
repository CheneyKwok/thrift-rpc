package github.cheneykwok.client;

import github.cheneykwok.RpcClientsRegistrar;
import github.cheneykwok.client.pool.ThriftClientKeyedPooledObjectFactory;
import github.cheneykwok.client.pool.ThriftClientPool;
import github.cheneykwok.client.properties.ClientPoolProperties;
import github.cheneykwok.client.properties.ClientProperties;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.TServiceClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.time.Duration;


@AutoConfiguration
@ConditionalOnProperty(prefix = "rpc.client", name = {"enabled"}, havingValue = "true")
@Import(RpcClientsRegistrar.class)
@EnableConfigurationProperties({ClientProperties.class, ClientPoolProperties.class})
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
    public ClientContext clientContext(ClientProperties clientProperties, ThriftClientPool clientPool) {
        return ClientContext.context(clientProperties, clientPool);
    }

}
