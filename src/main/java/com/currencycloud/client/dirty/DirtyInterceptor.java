package com.currencycloud.client.dirty;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class DirtyInterceptor implements MethodInterceptor {

    private final Object watched;
    private Set<String> dirtyProperties = new HashSet<>();

    public Set<String> getDirtyProperties() {
        return dirtyProperties;
    }

    public DirtyInterceptor(Object watched) {
        this.watched = watched;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String property = DirtyWatcherInterceptor.getPropertyFromSetter(method);
        if (property != null) {
            dirtyProperties.add(property);
        }
        return method.invoke(watched, args);
    }
}
