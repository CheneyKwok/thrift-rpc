package github.cheneykwok.server.support;

import github.cheneykwok.ThriftConstant;

import java.util.Arrays;

public final class ThriftServiceWrapperFactory {

    public static ThriftServiceWrapper wrapper( Object thriftService) {
        ThriftServiceWrapper thriftServiceWrapper = new ThriftServiceWrapper(thriftService);

        Class<?> thriftServiceIFace = Arrays.stream(thriftService.getClass().getInterfaces())
                .filter(iFace -> iFace.getName().endsWith(ThriftConstant.THRIFT_IFACE))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No thrift IFace found on service: " + thriftService));

        thriftServiceWrapper.setType(thriftService.getClass());
        thriftServiceWrapper.setServiceIFace(thriftServiceIFace);

        return thriftServiceWrapper;
    }

}
