package github.cheneykwok.server.support;

import github.cheneykwok.server.properties.ThriftServerProperties;
import org.apache.thrift.server.TServer;

import java.util.List;

/**
 * 单线程阻塞式构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public class SimpleThriftServerBuilder implements ThriftServerBuilder {

    @Override
    public TServer buildtServer(ThriftServerProperties properties, List<ThriftServiceWrapper> serviceWrappers) {
//        new TSimpleServer()
        return null;
    }
}
