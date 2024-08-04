package github.cheneykwok.server.builder;

import github.cheneykwok.ThriftTProtocolFactory;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.properties.TThreadedSelectorServerProperties;
import github.cheneykwok.server.support.TProcessorFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池选择器服务模型构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class TThreadedSelectorServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {

        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(properties.getPort());

            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(socket);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(ThriftTProtocolFactory.getProtocolFactory(properties.getProtocol()));
            args.processor(TProcessorFactory.buildProcessor(serviceWrappers));

            TThreadedSelectorServerProperties selectorProperties = properties.getThreadedSelector();
            args.selectorThreads(selectorProperties.getSelectorThreads());
            args.acceptQueueSizePerThread(selectorProperties.getAcceptQueueSizePerThread());
            ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                    selectorProperties.getCoreWorkerThreads(),
                    selectorProperties.getMaxWorkerThreads(),
                    selectorProperties.getKeepAliveTime(),
                    selectorProperties.getTimeUnit(),
                    new LinkedBlockingQueue<>(selectorProperties.getWorkerQueueCapacity()));
            args.executorService(executorService);
            return new TThreadedSelectorServer(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build TThreadedSelectorServer", e);
        }
    }
}
