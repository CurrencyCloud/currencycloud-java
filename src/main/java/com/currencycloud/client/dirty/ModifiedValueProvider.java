package com.currencycloud.client.dirty;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ModifiedValueProvider implements MethodInterceptor {

    private Map<String, Object> properties = new HashMap<>();

    public ModifiedValueProvider(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (ReflectionUtils.isGetter(method)) {
            return properties.get(ReflectionUtils.getPropertyFromGetter(method));
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }
}
