package github.cheneykwok.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@Data
@ConfigurationProperties(prefix = "rpc.server.threaded-selector")
public class TThreadedSelectorServerProperties {

    /**
     * 接收连接的Selector线程数量
     */
    private int selectorThreads = 2;

    /**
     * 每个Selector线程可接收连接的阻塞队列大小
     */
    private int acceptQueueSizePerThread = 4;

    /**
     * 核心工作线程数
     */
    private int coreWorkerThreads = 5;

    /**
     * 最大工作线程数
     */
    private int maxWorkerThreads = 20;

    /**
     * 非核心空闲线程存活时间
     */
    private int keepAliveTime = 60;

    /**
     * keepAliveTime参数的时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 工作线程队列容量
     */
    private int workerQueueCapacity = 1000;


}
