package github.cheneykwok.server.properties;

import github.cheneykwok.server.support.SimpleThriftServerBuilder;
import github.cheneykwok.server.support.ThriftServerBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    SIMPLE("simple", new SimpleThriftServerBuilder());

//    /**
//     * 单线程非阻塞式
//     */
//    NON_BLOCKING("nonBlocking"),
//
//    /**
//     * 线程池
//     */
//    THREAD_POOL("threadPool"),
//
//    /**
//     * 半同步半异步
//     */
//    HS_HA("hsHa"),
//
//    /**
//     * 线程池选择器
//     */
//    THREADED_SELECTOR("threadedSelector");

    private final String model;

    private final ThriftServerBuilder builder;

}
