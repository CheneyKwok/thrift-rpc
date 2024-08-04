package github.cheneykwok.server.builder;

import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.properties.THsHaServerProperties;
import github.cheneykwok.server.support.TProcessorFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.util.List;

/**
 * 半同步半异步服务模型构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class THsHaServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {

        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(properties.getPort());

            THsHaServer.Args args = new THsHaServer.Args(socket);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(ThriftTProtocolFactory.getProtocolFactory(properties.getProtocol()));
            args.processor(TProcessorFactory.buildProcessor(serviceWrappers));

            THsHaServerProperties poolProperties = properties.getHsHa();
            args.minWorkerThreads(poolProperties.getMinWorkerThreads());
            args.maxWorkerThreads(poolProperties.getMaxWorkerThreads());
            return new THsHaServer(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build THsHaServer", e);
        }
    }
}
