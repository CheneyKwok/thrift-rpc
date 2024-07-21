package github.cheneykwok.service;

import com.alibaba.fastjson2.JSON;
import github.cheneykwok.RpcService;
import github.cheneykwok.thrift.impl.RpcServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
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
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(server::serve);
            if (server.isServing()) {
                log.info("rpc server start success");
            }
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }


    public static String keyGenerator(Class<?> clazz, Method method) {
        Map<String, Object> container = new HashMap<>(3);
        // 类地址
        container.put("class", clazz.toGenericString());
        // 方法名称
        container.put("methodName", method.getName());

        Parameter[] parameters = method.getParameters();

        // 参数列表
        for (int i = 0; i < parameters.length; i++) {
            container.put(String.valueOf(i), parameters[i]);
        }
        String jsonString = JSON.toJSONString(container);
        // 做 SHA256 Hash 计算，得到一个 SHA256 摘要作为 Key
        return DigestUtils.sha256Hex(jsonString);
    }

}
