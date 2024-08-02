package github.cheneykwok;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author gzc
 * @date 2024-07-31
 */
@Data
public class MappingMethod {

    private Object bean;

    private BeanFactory beanFactory;

    private  Class<?> beanType;

    private Method method;

    private String path;

    public MappingMethod(Object bean, Class<?> beanType, Method method, String path, BeanFactory beanFactory) {
        this.bean = bean;
        this.beanType = beanType;
        this.method = method;
        this.path = path;
        this.beanFactory = beanFactory;
    }

    public Object doInvoke(Object... args) {
        Object actualBean;
        if (this.bean instanceof String beanName) {
            Assert.state(this.beanFactory != null, "Cannot resolve bean name without BeanFactory");
            actualBean = this.beanFactory.getBean(beanName);
            this.bean = actualBean;
        }
        return ReflectionUtils.invokeMethod(method, bean, args);
    }
}
