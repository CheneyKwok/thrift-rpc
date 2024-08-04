package github.cheneykwok.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc.server")
public class ServerProperties {

    private boolean enabled = false;

    /**
     * 服务端口号
     */
    private int port = 40880;

    /**
     * 服务模型(单线程/多线程/阻塞/非阻塞)
     * <p>
     * simple: 单线程阻塞模型
     * nonBlocking: 单线程非阻塞模型
     * threadPool: 线程池同步模型
     * hsHa: 半同步半异步模型
     * threadedSelector: 线程池选择器模型
     * </p>
     */
    private TServerModel model = TServerModel.THREADED_SELECTOR;

    private TProtocolType protocol = TProtocolType.TCompactProtocol;

    private TThreadPoolServerProperties threadPool = new TThreadPoolServerProperties();

    private THsHaServerProperties hsHa = new THsHaServerProperties();

    private TThreadedSelectorServerProperties threadedSelector = new TThreadedSelectorServerProperties();


}
