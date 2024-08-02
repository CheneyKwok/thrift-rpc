package github.cheneykwok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gzc
 * @date 2024-07-31
 */
@Component
public class RpcMappingHandler implements InitializingBean, ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";

    private ApplicationContext applicationContext;

    private final Map<String, MappingMethod> methodMappings = new HashMap<>();

    public boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, RpcMapping.class);
    }

    @Override
    public void afterPropertiesSet() {
        handlerMethods();
    }

    private void handlerMethods() {
        String[] candidateBeanNames = getCandidateBeanNames();
        for (String beanName : candidateBeanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                processCandidateBean(beanName);
            }
        }
    }

    private void processCandidateBean(String beanName) {
        Class<?> beanType = null;
        try {
            beanType = applicationContext.getType(beanName);
        }
        catch (Exception ex) {
            // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                log.warn("Could not resolve type for bean '" + beanName + "'", ex);
        }
        if (beanType != null && isHandler(beanType)) {
            detectHandlerMethods(beanName, beanType);
        }
    }

    private void detectHandlerMethods(String beanName, Class<?> beanType) {
        if (beanType != null) {
            // 获取类的用户定义类：通常只是给定的类，但如果是 CGLIB 生成的子类，则返回原始类
            Class<?> userType = ClassUtils.getUserClass(beanType);

            Map<Method, MappingMethod> methods = MethodIntrospector.selectMethods(userType,
                    (MethodIntrospector.MetadataLookup<MappingMethod>) method -> getMappingForMethod(method, beanName, userType));

            for (MappingMethod mappingMethod : methods.values()) {
                methodMappings.put(mappingMethod.getPath(), mappingMethod);
            }
        }
    }

    protected MappingMethod getMappingForMethod(Method method, String beanName, Class<?> beanType) {
        RpcMapping methodMapping = AnnotatedElementUtils.findMergedAnnotation(method, RpcMapping.class);
        if (methodMapping == null || !StringUtils.hasText(methodMapping.value())) {
            throw new IllegalStateException("@RpcMapping value must not be empty, method: " + method.toGenericString());
        }
        String path = methodMapping.value();
        RpcMapping classMapping = AnnotatedElementUtils.findMergedAnnotation(beanType, RpcMapping.class);
        if (classMapping != null && StringUtils.hasText(classMapping.value())) {
            path = classMapping.value() + path;
        }

        return new MappingMethod(beanName, beanType, method, path, applicationContext.getAutowireCapableBeanFactory());
    }


    private String[] getCandidateBeanNames() {
        return applicationContext.getBeanNamesForType(Object.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public MappingMethod getMethodMapping(String path) {
        return methodMappings.get(path);
    }
}
