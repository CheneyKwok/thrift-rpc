package github.cheneykwok;

import github.cheneykwok.client.ServiceKey;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gzc
 * @date 2024-07-26
 */
@Data
public class RpcRequestTemplate {

    private Map<String, String> header = new HashMap<>();

    private ServiceKey serviceKey;

    private MethodMetadata methodMetadata;

}
