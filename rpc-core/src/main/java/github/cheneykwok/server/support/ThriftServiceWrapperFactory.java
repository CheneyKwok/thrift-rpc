package github.cheneykwok.server.support;

import github.cheneykwok.client.ThriftConstant;

import java.util.Arrays;

public final class ThriftServiceWrapperFactory {

    public static ThriftServiceWrapper wrapper( Object thriftService) {
        ThriftServiceWrapper thriftServiceWrapper = new ThriftServiceWrapper(thriftService);

        Class<?> thriftServiceIFace = Arrays.stream(thriftService.getClass().getInterfaces())
                .filter(iFace -> iFace.getName().endsWith(ThriftConstant.THRIFT_IFACE))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No thrift IFace found on service"));

        thriftServiceWrapper.setType(thriftService.getClass());
        thriftServiceWrapper.setServiceIFace(thriftServiceIFace);

        return thriftServiceWrapper;
    }

}
