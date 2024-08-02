package github.cheneykwok.client;

import lombok.Builder;
import lombok.Data;

/**
 * 服务 key
 *
 * @author gzc
 * @date 2024-07-29
 */
@Data
@Builder
public class ServiceKey {

    private String host;

    private int port;

    private int connectTimeout;

    private Class<?> serviceInterfaceClass;

}
