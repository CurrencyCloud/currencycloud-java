package com.currencycloud.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonTestSupport {

    private final SimpleDateFormat timeFormat;
    private final SimpleDateFormat dateFormat;

    public JsonTestSupport() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    protected Date parseDateTime(String str) {
        try {
            return timeFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse time: " + str);
        }
    }

    protected Date parseDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse date: " + str);
        }
    }
}
