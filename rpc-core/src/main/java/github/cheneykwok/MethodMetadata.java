package github.cheneykwok;

import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author gzc
 * @date 2024-07-26
 */
@Data
public class MethodMetadata {

    private Type returnType;

    private Class<?> targetType;

    private Method method;

    private Object[] args;
}
