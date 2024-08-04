package github.cheneykwok.server;

import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.support.ThriftServerFactory;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import java.util.List;

/**
 * @author gzc
 * @date 2024-08-02
 */
public class ServerBootstrap implements SmartLifecycle {

    private ServerProperties serverProperties;

    private List<ThriftServiceWrapper> serviceWrappers;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private TServer server;

    public ServerBootstrap(ServerProperties serverProperties, List<ThriftServiceWrapper> serviceWrappers) {
        this.serverProperties = serverProperties;
        this.serviceWrappers = serviceWrappers;
    }

    @Override
    public void start() {

        ThriftServerFactory factory = new ThriftServerFactory(serverProperties, serviceWrappers);
        server = factory.build(serverProperties.getModel());
        ThriftRunner runner = new ThriftRunner(server);
        new Thread(runner, "thrift-server").start();
        new Thread(() -> {
            while (true) {
                if (server.isServing()) {
                    log.info("Rpc server started on port: {} (tcp) successfully", serverProperties.getPort());
                    break;
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        if (isRunning()) {
            log.info("Shutting down thrift server");
            server.setShouldStop(true);
            server.stop();
        }
    }

    @Override
    public boolean isRunning() {
        return server != null && server.isServing();
    }

    private static class ThriftRunner implements Runnable {
        private final TServer server;

        private final Logger log = LoggerFactory.getLogger(getClass());

        public ThriftRunner(TServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            if (server != null) {
                server.serve();
            }
        }
    }
}
