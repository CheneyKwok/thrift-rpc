package github.cheneykwok.server.builder;

import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.support.TProcessorFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.util.List;

/**
 * 单线程非阻塞式服务模型构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class TNonBlockingServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {

        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(properties.getPort());

            TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(ThriftTProtocolFactory.getProtocolFactory(properties.getProtocol()));
            args.processor(TProcessorFactory.buildProcessor(serviceWrappers));
            return new TNonblockingServer(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build TNonblockingServer", e);
        }
    }
}
