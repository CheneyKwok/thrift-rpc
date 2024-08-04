package github.cheneykwok.server.support;

import github.cheneykwok.ThriftConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThriftServiceHandler implements BeanPostProcessor {

    private final List<ThriftServiceWrapper> serviceWrappers =new ArrayList<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        Arrays.stream(interfaces)
                .filter(i -> i.getName().endsWith(ThriftConstant.THRIFT_IFACE))
                .forEach(i -> serviceWrappers.add(ThriftServiceWrapperFactory.wrapper(bean)));
        return bean;
    }

    public List<ThriftServiceWrapper> getServiceWrappers() {
        return serviceWrappers;
    }

    public void addServiceWrapper(ThriftServiceWrapper wrapper) {
        serviceWrappers.add(wrapper);
    }

}
