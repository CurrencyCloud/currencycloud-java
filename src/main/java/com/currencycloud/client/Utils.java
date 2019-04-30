package com.currencycloud.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Utils {
    ;

    public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssX";

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
        if (strings == null || separator == null) {
            return null;
        }

        List<String> reversed = new ArrayList<>(strings);
        Collections.reverse(reversed);
        return String.join(separator, reversed);
    }

    public static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }
}
