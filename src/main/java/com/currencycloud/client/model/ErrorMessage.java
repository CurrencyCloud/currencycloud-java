package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ErrorMessage {

    private String code;
    private String message;
    private Map<String, Object> params;

    /** The error message code */
    public String getCode() {
        return code;
    }

    /** The error message in English */
    public String getMessage() {
        return message;
    }

    /** Error parameters (may be used eg. for rendering the error message in other languages */
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("ErrorMessage{code='%s', message='%s', params=%s}", code, message, params);
    }
}
