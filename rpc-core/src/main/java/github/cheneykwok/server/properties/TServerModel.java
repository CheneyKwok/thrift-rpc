package github.cheneykwok.server.properties;

import github.cheneykwok.server.builder.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * Thrift 服务模型
 * 
 * @author gzc
 * @date 2024-08-02
 */
@Getter
@AllArgsConstructor
public enum TServerModel {

    /**
     * 单线程阻塞式
     */
    SIMPLE("simple", SimpleServerBuilder::new),

    /**
     * 单线程非阻塞式
     */
    NON_BLOCKING("nonBlocking", TNonBlockingServerBuilder::new),

    /**
     * 线程池
     */
    THREAD_POOL("threadPool", TThreadPoolServerBuilder::new),

    /**
     * 半同步半异步
     */
    HS_HA("hsHa", THsHaServerBuilder::new),

    /**
     * 线程池选择器
     */
    THREADED_SELECTOR("threadedSelector", TThreadedSelectorServerBuilder::new);

    private final String model;

    private final Supplier<ThriftServerBuilder> builder;

}
