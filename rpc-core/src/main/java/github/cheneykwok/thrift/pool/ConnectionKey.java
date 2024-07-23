package github.cheneykwok.thrift.pool;

import github.cheneykwok.thrift.ServiceProperty;
import lombok.Data;


@Data
public class ConnectionKey {

    private Class<?> tServiceClientClass;

    private ServiceProperty serviceProperty;

    private int connectTimeout;

}
