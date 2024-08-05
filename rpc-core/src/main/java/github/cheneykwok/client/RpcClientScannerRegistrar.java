package github.cheneykwok.client;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RPC 客户端扫描注册器
 *
 * @author gzc
 * @date 2024-08-05
 */
public class RpcClientScannerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String property = environment.getProperty("rpc.client.enabled");
        if (!Boolean.parseBoolean(property)) {
            return;
        }
        AnnotationAttributes scanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(RpcClientScan.class.getName()));
        if (scanAttrs != null) {
            registerBeanDefinitions(importingClassMetadata, scanAttrs, registry);
        }
    }

    void registerBeanDefinitions(AnnotationMetadata annoMeta, AnnotationAttributes scanAttrs, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RpcClientScannerConfigurer.class);

        String[] values = scanAttrs.getStringArray("value");
        if (scanAttrs.isEmpty()) {
            values = scanAttrs.getStringArray("basePackages");
        }
        List<String> basePackages = Arrays.stream(values)
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());

        if (basePackages.isEmpty()) {
            basePackages.add(getDefaultBasePackage(annoMeta));
        }

        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));
        String beanName = generateBaseBeanName(annoMeta, 0);
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + RpcClientScannerRegistrar.class.getSimpleName() + "#" + index;
    }

    private static String getDefaultBasePackage(AnnotationMetadata importingClassMetadata) {
        return ClassUtils.getPackageName(importingClassMetadata.getClassName());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
