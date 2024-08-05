package github.cheneykwok;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MappingRegistry {

    private final Map<String, MappingMethod> pathLookup = new HashMap<>();
    private final Map<Method, MappingMethod> methodLookup = new HashMap<>();

    public void register(MappingMethod mappingMethod) {
        String path = mappingMethod.getPath();
        Method method = mappingMethod.getMethod();
        if (path != null) {
            pathLookup.put(path, mappingMethod);
        }
        if (method != null) {
            methodLookup.put(method, mappingMethod);
        }
    }

    public MappingMethod getMapping(String path) {
        return pathLookup.get(path);
    }

    public MappingMethod getMapping(Method method) {
        return methodLookup.get(method);
    }

}
