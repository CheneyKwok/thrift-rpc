package github.cheneykwok.service;

import github.cheneykwok.RpcService;
import github.cheneykwok.thrift.impl.RpcServiceImpl;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    private final Logger log = LoggerFactory.getLogger(RpcApplicationListener.class);

    private final RpcServiceProvider rpcServiceProvider;

    private final RpcServiceImpl rpcServiceImpl;

    public RpcApplicationListener(RpcServiceProvider rpcServiceProvider, RpcServiceImpl rpcServiceImpl) {
        this.rpcServiceProvider = rpcServiceProvider;
        this.rpcServiceImpl = rpcServiceImpl;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        ApplicationContext context = event.getApplicationContext();
        if (event instanceof ContextRefreshedEvent || event instanceof ContextStartedEvent) {
            initRpcServer(context);
        }
    }

    private void initRpcServer(ApplicationContext context) {
        Map<String, Object> enableRpcBeans = context.getBeansWithAnnotation(EnableRpcService.class);
        if (enableRpcBeans.isEmpty()) {
            return;
        }
        Map<String, Object> rpcServiceBeans = context.getBeansWithAnnotation(RpcService.class);
        if (!rpcServiceBeans.isEmpty()) {
            rpcServiceBeans.values().forEach(rpcServiceProvider::addService);
        }
        try {
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(7777);
            TThreadedSelectorServer.Args serverParams = new TThreadedSelectorServer.Args(serverTransport);
            serverParams.protocolFactory(new TCompactProtocol.Factory());
            serverParams.processor(new github.cheneykwok.thrift.gen.RpcService.Processor<>(rpcServiceImpl));
            TThreadedSelectorServer server = new TThreadedSelectorServer(serverParams);
            ExecutorService executor = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(1);
            executor.execute(server::serve);
            executor.execute(() -> {
                while (true) {
                    if (server.isServing()) {
                        log.info("RPC started on port: 7777 (tcp) ");
                        latch.countDown();
                        break;
                    }
                }
            });
            latch.await();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
