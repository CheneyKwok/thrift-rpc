package github.cheneykwok.server.builder;

import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.properties.TThreadPoolServerProperties;
import github.cheneykwok.server.support.TProcessorFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;

import java.net.ServerSocket;
import java.util.List;

/**
 * 线程池服务模型构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class TThreadPoolServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {

        try {
            TServerSocket socket = new TServerSocket(new ServerSocket(properties.getPort()));

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(socket);
            args.transportFactory(new TTransportFactory());
            args.protocolFactory(ThriftTProtocolFactory.getProtocolFactory(properties.getProtocol()));
            args.processor(TProcessorFactory.buildProcessor(serviceWrappers));

            TThreadPoolServerProperties poolProperties = properties.getThreadPool();
            args.minWorkerThreads(poolProperties.getMinWorkerThreads());
            args.maxWorkerThreads(poolProperties.getMaxWorkerThreads());
            return new TThreadPoolServer(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build TThreadPoolServer", e);
        }
    }
}
