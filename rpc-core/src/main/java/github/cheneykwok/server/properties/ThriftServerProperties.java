package github.cheneykwok.server.properties;

import github.cheneykwok.client.properties.TServiceModel;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rpc.thrift.server")
public class ThriftServerProperties {

    /**
     * 服务端口号
     */
    private int port = 25000;

    /**
     * 服务的工作线程队列容量
     */
    private int workerQueueCapacity = 1000;

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
    private String model = TServiceModel.SERVICE_MODEL_DEFAULT;

    private TThreadPoolServerProperties threadPool;

    private THsHaServerProperties hsHa;

    private TThreadedSelectorServerProperties threadedSelector;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWorkerQueueCapacity() {
        return workerQueueCapacity;
    }

    public void setWorkerQueueCapacity(int workerQueueCapacity) {
        this.workerQueueCapacity = workerQueueCapacity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TThreadPoolServerProperties getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(TThreadPoolServerProperties threadPool) {
        this.threadPool = threadPool;
    }

    public THsHaServerProperties getHsHa() {
        return hsHa;
    }

    public void setHsHa(THsHaServerProperties hsHa) {
        this.hsHa = hsHa;
    }

    public TThreadedSelectorServerProperties getThreadedSelector() {
        return threadedSelector;
    }

    public void setThreadedSelector(TThreadedSelectorServerProperties threadedSelector) {
        this.threadedSelector = threadedSelector;
    }
}
