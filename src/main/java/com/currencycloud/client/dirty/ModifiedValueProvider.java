package com.currencycloud.client.dirty;


import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ModifiedValueProvider {

    private Map<String, Object> properties = new HashMap<>();

    public ModifiedValueProvider(Map<String, Object> properties) {
        this.properties = properties;
    }

    @RuntimeType
    public Object intercept(@This Object obj, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method proxy) throws Throwable {
        if (ReflectionUtils.isGetter(method)) {
            return properties.get(ReflectionUtils.getPropertyFromGetter(method));
        } else {
            return proxy.invoke(obj, args);
        }
    }
}
