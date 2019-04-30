package com.currencycloud.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Utils {
    ;

    public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssX";

    public static String join(List<String> strings, String separator) {
        if (!validInput(strings, separator)) {
            return null;
        }
        return String.join(separator, strings);
    }

    public static String joinInverse(List<String> strings, String separator) {
        if (!validInput(strings, separator)) {
            return null;
        }

        List<String> reversed = new ArrayList<>(strings);
        Collections.reverse(reversed);
        return join(reversed, separator);
    }

    private static boolean validInput(List<String> strings, String separator) {
        return strings != null && separator != null;
    }

    public static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }
}
