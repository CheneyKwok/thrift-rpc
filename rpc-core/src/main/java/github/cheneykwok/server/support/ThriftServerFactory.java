package github.cheneykwok.server.support;

import github.cheneykwok.server.builder.ThriftServerBuilder;
import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.properties.TServerModel;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzc
 * @date 2024-08-02
 */
public class ThriftServerFactory {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final Map<String, ThriftServerBuilder> SERVER_BUILDER_MAP;

    private ServerProperties properties;

    private List<ThriftServiceWrapper> serviceWrappers;

    public ThriftServerFactory(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {
        this.properties = properties;
        this.serviceWrappers = serviceWrappers;
    }

    static {
        SERVER_BUILDER_MAP = new HashMap<>();
        Arrays.stream(TServerModel.values())
                .forEach(tServerModel -> SERVER_BUILDER_MAP.put(tServerModel.getModel(), tServerModel.getBuilder().get()));
    }

    public TServer build(TServerModel tServerModel) {
        ThriftServerBuilder builder = SERVER_BUILDER_MAP.get(tServerModel.getModel());
        if (builder == null) {
            builder = SERVER_BUILDER_MAP.get(TServerModel.SIMPLE.getModel());
            log.info("The configured server model was not found,, use the default: {}", TServerModel.SIMPLE.getModel());
        }
        return builder.buildtServer(properties, serviceWrappers);
    }

}
