package github.cheneykwok.thrift.pool;

import github.cheneykwok.thrift.ServiceProperty;


public class ConnectionKey {

    private Class<?> tServiceClientClass;

    private ServiceProperty serviceProperty;

    private int connectTimeout;

    public Class<?> gettServiceClientClass() {
        return tServiceClientClass;
    }

    public void settServiceClientClass(Class<?> tServiceClientClass) {
        this.tServiceClientClass = tServiceClientClass;
    }

    public ServiceProperty getServiceProperty() {
        return serviceProperty;
    }

    public void setServiceProperty(ServiceProperty serviceProperty) {
        this.serviceProperty = serviceProperty;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @Override
    public String toString() {
        return "ConnectionKey{" +
                "tServiceClientClass=" + tServiceClientClass +
                ", serviceProperty=" + serviceProperty +
                ", connectTimeout=" + connectTimeout +
                '}';
    }
}
