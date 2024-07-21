package github.cheneykwok;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对RpcClient注解的接口进行属性解析，然后转换成BeanDefinition纳入容器管理
 * @author gzc
 * @date 2024-07-21
 */
public class RpcClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerRpcClients(importingClassMetadata, registry);
    }

    private void registerRpcClients(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
        Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(EnableRpcClient.class.getName());
        AnnotationAttributes enableAttrs = AnnotationAttributes.fromMap(attrs);
        if (enableAttrs == null) {
            throw new IllegalStateException("EnableRpcClient is not present");
        }
        Set<String> basePackages = getBasePackages(importingClassMetadata, enableAttrs);
        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                // verify annotated class is an interface
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@RpcClient can only be specified on an interface");

                attrs = annotationMetadata.getAnnotationAttributes(RpcClient.class.getCanonicalName());
                AnnotationAttributes rpcClientAttrs = AnnotationAttributes.fromMap(attrs);
                if (rpcClientAttrs == null) {
                    continue;
                }

                registerRpcClient(registry, annotationMetadata, rpcClientAttrs);
            }
        }

    }

    private void registerRpcClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, AnnotationAttributes rpcClientAttrs) {
        String className = annotationMetadata.getClassName();
        Class clazz = ClassUtils.resolveClassName(className, null);
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory) registry : null;
        String rpcServiceName = rpcClientAttrs.getString("name");
        if (!StringUtils.hasText(rpcServiceName)) {
            throw new IllegalStateException("'name' must be provided in @" + RpcClient.class.getSimpleName());
        }
        RpcClientFactoryBean factoryBean = new RpcClientFactoryBean();
        factoryBean.setBeanFactory(beanFactory);
        factoryBean.setType(clazz);
        factoryBean.setName(rpcServiceName);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz, factoryBean::getObject);
        beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        beanDefinitionBuilder.setLazyInit(true);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata, AnnotationAttributes enableAttrs) {
        Set<String> basePackages = new HashSet<>();
        for (String pkg : enableAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }

        return basePackages;
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

}
