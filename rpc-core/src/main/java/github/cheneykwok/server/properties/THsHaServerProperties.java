package github.cheneykwok.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc.server.hs-ha")
public class THsHaServerProperties {

    /**
     * 最少工作线程数量
     */
    private int minWorkerThreads = 5;

    /**
     * 最多工作线程数量
     */
    private int maxWorkerThreads = 20;

    /**
     * 服务的工作线程队列容量
     */
    private int workerQueueCapacity = 1000;

}
