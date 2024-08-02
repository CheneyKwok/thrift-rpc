package github.cheneykwok.server.support;

import github.cheneykwok.server.properties.ThriftServerProperties;
import org.apache.thrift.server.TServer;

import java.util.List;

/**
 * Thrift Server 构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public interface ThriftServerBuilder {

    TServer buildtServer(ThriftServerProperties properties, List<ThriftServiceWrapper> serviceWrappers);
}
