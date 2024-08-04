package github.cheneykwok.server.builder;

import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.support.TProcessorFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;

import java.net.ServerSocket;
import java.util.List;

/**
 * 单线程阻塞式服务模型构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class SimpleServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {

        try {
            TServerSocket socket = new TServerSocket(new ServerSocket(properties.getPort()));

            TSimpleServer.Args args = new TSimpleServer.Args(socket);
            args.transportFactory(new TTransportFactory());
            args.protocolFactory(ThriftTProtocolFactory.getProtocolFactory(properties.getProtocol()));
            args.processor(TProcessorFactory.buildProcessor(serviceWrappers));
            return new TSimpleServer(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build TSimpleServer", e);
        }
    }
}
