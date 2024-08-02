package github.cheneykwok.server.support;

public class ThriftServiceWrapper {

    private Class<?> type;

    private Class<?> serviceIFace;

    private final Object thriftService;


    public ThriftServiceWrapper(Object thriftService) {
        this.thriftService = thriftService;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getServiceIFace() {
        return serviceIFace;
    }

    public void setServiceIFace(Class<?> serviceIFace) {
        this.serviceIFace = serviceIFace;
    }

    public Object getThriftService() {
        return thriftService;
    }
}
