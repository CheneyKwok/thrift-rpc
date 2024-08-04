package github.cheneykwok.server;

import github.cheneykwok.RpcMappingHandler;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.properties.THsHaServerProperties;
import github.cheneykwok.server.properties.TThreadPoolServerProperties;
import github.cheneykwok.server.properties.TThreadedSelectorServerProperties;
import github.cheneykwok.server.support.ThriftServiceHandler;
import github.cheneykwok.server.support.ThriftServiceWrapperFactory;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import github.cheneykwok.thrift.impl.InnerRpcServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@ConditionalOnProperty(prefix = "rpc.server", name = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties({ServerProperties.class, TThreadPoolServerProperties.class,
        THsHaServerProperties.class, TThreadedSelectorServerProperties.class})
public class RpcServerAutoConfiguration {

    @Bean
    public InnerRpcService.Iface innerRpcService(RpcMappingHandler rpcMappingHandler) {
        return new InnerRpcServiceImpl(rpcMappingHandler);
    }

    @Bean
    public ThriftServiceHandler thriftServiceHandler(InnerRpcService.Iface innerRpcService) {
        ThriftServiceHandler thriftServiceHandler = new ThriftServiceHandler();
        thriftServiceHandler.addServiceWrapper(ThriftServiceWrapperFactory.wrapper(innerRpcService));
        return thriftServiceHandler;
    }

    @Bean
    public ServerBootstrap serverBootstrap(ServerProperties serverProperties, ThriftServiceHandler thriftServiceHandler) {
        return new ServerBootstrap(serverProperties, thriftServiceHandler.getServiceWrappers());
    }

}
