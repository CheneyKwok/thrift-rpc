package github.cheneykwok.server.support;

import github.cheneykwok.server.properties.TServerModel;
import github.cheneykwok.server.properties.ThriftServerProperties;
import org.apache.thrift.server.TServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzc
 * @date 2024-08-02
 */
public class ThriftServerFactory {

    private static final Map<String, ThriftServerBuilder> SERVER_BUILDER_MAP;

    private ThriftServerProperties properties;

    private List<ThriftServiceWrapper> serviceWrappers;

    public ThriftServerFactory(ThriftServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {
        this.properties = properties;
        this.serviceWrappers = serviceWrappers;
    }

    static {
        SERVER_BUILDER_MAP = new HashMap<>();
        Arrays.stream(TServerModel.values())
                .forEach(tServerModel -> SERVER_BUILDER_MAP.put(tServerModel.getModel(), tServerModel.getBuilder()));
    }

    public TServer build(TServerModel tServerModel) {
        ThriftServerBuilder builder = SERVER_BUILDER_MAP.get(tServerModel.getModel());
        return builder.buildtServer(properties, serviceWrappers);
    }

}
