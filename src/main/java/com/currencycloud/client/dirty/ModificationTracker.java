package com.currencycloud.client.dirty;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModificationTracker {

    private final Object watched;
    private Map<String, Object> dirtyProperties = new HashMap<>();

    public Map<String, Object> getDirtyProperties() {
        return dirtyProperties;
    }

    public ModificationTracker(Object watched) {
        this.watched = watched;
    }

    @RuntimeType
    public Object intercept(@This Object obj, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method proxy) throws Throwable {
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
