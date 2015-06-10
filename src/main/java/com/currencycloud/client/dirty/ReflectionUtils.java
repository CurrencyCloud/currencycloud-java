package com.currencycloud.client.dirty;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getName().length() > 3
                && method.getParameterTypes().length == 1
                && method.getReturnType().equals(Void.TYPE);
    }

    public static boolean isGetter(Method method) {
        return method.getName().startsWith("get")
                && method.getName().length() > 3
                && method.getParameterTypes().length == 0
                && !method.getReturnType().equals(Void.TYPE);
    }

    @Nullable
    public static String getPropertyFromSetter(Method method) {
        if (!isSetter(method)) {
            return null;
        }
        char[] chars = method.getName().substring(3).toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    @Nullable
    public static String getPropertyFromGetter(Method method) {
        if (!isGetter(method)) {
            return null;
        }
        char[] chars = method.getName().substring(3).toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static Method getGetterFromProperty(Class clazz, String property)
            throws NoSuchMethodException {
        char[] chars = property.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return clazz.getMethod("get" + new String(chars));
    }
}
