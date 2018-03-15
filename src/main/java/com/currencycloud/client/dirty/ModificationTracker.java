package com.currencycloud.client.dirty;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModificationTracker implements MethodInterceptor {

    private final Object watched;
    private Map<String, Object> dirtyProperties = new HashMap<>();

    public Map<String, Object> getDirtyProperties() {
        return dirtyProperties;
    }

    public ModificationTracker(Object watched) {
        this.watched = watched;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String property = ReflectionUtils.getPropertyFromSetter(method);
        if (property != null) {
            Method getter;
            try {
                getter = ReflectionUtils.getGetterFromProperty(obj.getClass(), property);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("A probable bug in the Currencycloud SDK: no getter found for setter " + method, e);
            }
            Object prevPropertyValue = getter.invoke(obj);
            Object newPropertyValue = args[0];
            if (!Objects.equals(prevPropertyValue, newPropertyValue)) {
                dirtyProperties.put(property, newPropertyValue);
            }
        }
        return method.invoke(watched, args);
    }
}
