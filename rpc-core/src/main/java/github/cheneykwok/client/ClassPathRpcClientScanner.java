package github.cheneykwok.client;

import github.cheneykwok.ThriftConstant;
import github.cheneykwok.thrift.gen.inner.InnerRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * RPC 客户端扫描
 *
 * <p>解析 RpcClient 注解的接口，转换成 BeanDefinition 纳入容器管理</p>
 *
 * @author gzc
 * @date 2024-08-05
 */
public class ClassPathRpcClientScanner extends ClassPathBeanDefinitionScanner {


    private static final Logger logger = LoggerFactory.getLogger(ClassPathRpcClientScanner.class);


    public ClassPathRpcClientScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            logger.warn("No Rpc client was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitionHolders);
        }
        return beanDefinitionHolders;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }


    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        ScannedGenericBeanDefinition beanDefinition;
        AnnotationAttributes attributes;
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {

            if (beanDefinitionHolder.getBeanDefinition() instanceof ScannedGenericBeanDefinition) {
                beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                boolean hasAnnotation = annotationMetadata.hasAnnotation(RpcClient.class.getName());
                if (hasAnnotation) {

                    Assert.isTrue(annotationMetadata.isInterface(), "@RpcClient can only be specified on an interface");
                    BeanDefinitionRegistry registry = getRegistry();
                    String className = annotationMetadata.getClassName();
                    Class<?> clazz = ClassUtils.resolveClassName(className, null);
                    ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory) registry : null;
                    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                    propertyValues.add("beanFactory", beanFactory);
                    propertyValues.add("type", clazz);
                    propertyValues.add("ifaceClass", getIfaceClass(className));

                    attributes = AnnotationAttributes
                            .fromMap(annotationMetadata.getAnnotationAttributes(RpcClient.class.getName()));
                    if (attributes != null && !attributes.isEmpty()) {

                        String serverId = attributes.getString("serverId");
                        String address = attributes.getString("address");
                        if (!StringUtils.hasText(serverId) && !StringUtils.hasText(address)) {
                            throw new IllegalStateException("'serverId' or 'address' must be provided in @" + RpcClient.class.getSimpleName() + " on class: " + className);

                        }

                        propertyValues.add("serverId", serverId);
                        propertyValues.add("address", address);
                    }

                    beanDefinition.setLazyInit(true);
                    beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                    beanDefinition.setBeanClass(ClientFactoryBean.class);

                }
            }
        }
    }



    private Class<?> getIfaceClass(String className) {
        Class<?> ifaceClass;
        if (className.endsWith(ThriftConstant.THRIFT_IFACE)) {
            try {
                ifaceClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            ifaceClass = InnerRpcService.Iface.class;
        }
        return ifaceClass;
    }
}
