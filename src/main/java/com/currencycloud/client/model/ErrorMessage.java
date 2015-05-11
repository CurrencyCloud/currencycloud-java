package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ErrorMessage {

    private String code;
    private String message;
    private Map<String, Object> params;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("ErrorMessage{code='%s', message='%s', params=%s}", code, message, params);
    }
}
