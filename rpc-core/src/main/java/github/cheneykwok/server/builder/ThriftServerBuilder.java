package github.cheneykwok.server.builder;

import github.cheneykwok.server.properties.ServerProperties;
import github.cheneykwok.server.support.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;

import java.util.List;

/**
 * Thrift Server 构建器
 *
 * @author gzc
 * @date 2024-08-02
 */
public interface ThriftServerBuilder {

    TServer buildtServer(ServerProperties properties, List<ThriftServiceWrapper> serviceWrappers);
}
