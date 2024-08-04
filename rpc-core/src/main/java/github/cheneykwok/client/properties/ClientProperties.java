package github.cheneykwok.client.properties;

import github.cheneykwok.server.properties.TProtocolType;
import github.cheneykwok.server.properties.TServerModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * RPC 客户端配置
 *
 * @author gzc
 * @date 2024-07-29
 */
@Data
@ConfigurationProperties(prefix = "rpc.client")
public class ClientProperties {

    /**
     * 服务模型
     */
    private TServerModel serverModel = TServerModel.THREADED_SELECTOR;

    /**
     * 重试次数
     */
    private int retryTimes;

    private int connectTimeout = 10000;

    /**
     * 包对应的服务器地址
     * <P>
     * 格式: user: 127.0.0.1:8888
     * </P>
     */
    private final Map<String, String> serverAddrList = new HashMap<>();

//    /**
//     * 包对应的服务器地址
//     * <P>
//     * 格式: com.xxx.user: 127.0.0.1:8888
//     * </P>
//     */
//    private final Map<String, String> packageToServerAddr = new HashMap<>();

    private TProtocolType protocol = TProtocolType.TCompactProtocol;

    /**
     * 客户端对象池配置
     */
    private ClientPoolProperties pool = new ClientPoolProperties();

}
