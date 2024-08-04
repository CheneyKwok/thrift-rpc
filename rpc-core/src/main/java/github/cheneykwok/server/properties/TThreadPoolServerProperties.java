package github.cheneykwok.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc.server.thread-pool")
public class TThreadPoolServerProperties {

    /**
     * 最少工作线程的数量
     */
    private int minWorkerThreads = 5;

    /**
     * 最大工作线程的数量
     */
    private int maxWorkerThreads = 20;

}
