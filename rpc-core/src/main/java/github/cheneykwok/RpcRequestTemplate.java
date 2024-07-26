package github.cheneykwok;

import github.cheneykwok.thrift.pool.ConnectionKey;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gzc
 * @date 2024-07-26
 */
@Data
public class RpcRequestTemplate {

    private final Map<String, String> header = new HashMap<>();

    private ConnectionKey connectionKey;

    private MethodMetadata methodMetadata;

}
