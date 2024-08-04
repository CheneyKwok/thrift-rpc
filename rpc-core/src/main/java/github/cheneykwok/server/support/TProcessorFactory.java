package github.cheneykwok.server.support;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Stream;

public class TProcessorFactory {

    private static class TProcessorHolder {
        private static final TMultiplexedProcessor PROCESSOR = new TMultiplexedProcessor();
    }

    static TMultiplexedProcessor getRegisterProcessor() {
        return TProcessorHolder.PROCESSOR;
    }

    public static TProcessor buildProcessor(List<ThriftServiceWrapper> serviceWrappers) {
        TMultiplexedProcessor processor = getRegisterProcessor();
        for (ThriftServiceWrapper serviceWrapper : serviceWrappers) {
            Object bean = serviceWrapper.getThriftService();
            Class<?> ifaceClass = serviceWrapper.getServiceIFace();

            Class<TProcessor> processorClass =
                    Stream.of(ifaceClass.getDeclaringClass().getDeclaredClasses())
                    .filter(clazz -> clazz.getName().endsWith("$Processor"))
                    .filter(TProcessor.class::isAssignableFrom)
                    .findFirst()
                    .map(p -> (Class<TProcessor>) p)
                    .orElseThrow(() -> new IllegalStateException("No thrift Processor found on class: " + ifaceClass));

            Constructor<TProcessor> processorConstructor = null;
            try {
                processorConstructor = processorClass.getConstructor(ifaceClass);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            TProcessor singleProcessor = BeanUtils.instantiateClass(processorConstructor, bean);
            processor.registerProcessor(serviceWrapper.getServiceIFace().getName(), singleProcessor);
        }
        return processor;
    }
}
