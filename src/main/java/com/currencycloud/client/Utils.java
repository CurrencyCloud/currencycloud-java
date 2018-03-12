package com.currencycloud.client;

import java.util.List;

public enum Utils {
    ;

    public static String join(Iterable<String> strings, String separator) {
        if (strings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(string);
        }
        return sb.toString();
    }

    public static String joinInverse(List<String> strings, String separator) {
        if (strings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = strings.size() - 1; i >= 0; i--) {
            String string = strings.get(i);
            if (sb.length() > 0) {
                sb.append(separator);
            }
            sb.append(string);
        }
        return sb.toString();
    }

    public static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }
}
