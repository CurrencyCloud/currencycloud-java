package com.currencycloud.client.model;

import com.currencycloud.client.exception.ApiException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ErrorMessage {

    private String field;
    private String code;
    private String message;
    private Map<String, Object> params;

    protected ErrorMessage() { }

    public ErrorMessage(String field, String code, String message, Map<String, Object> params) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.params = params;
    }

    /** @return The error message code */
    public String getCode() {
        return code;
    }

    /** @return The error message in English */
    public String getMessage() {
        return message;
    }

    /** @return The field whose validation failed.
     * Note that this is only populated in {@link ApiException} and subclasses,
     * while it is always null in {@link ResponseException}. */
    public String getField() {
        return field;
    }

    /** @return Error parameters (may be used eg. for rendering the error message in other languages */
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return String.format("ErrorMessage{code='%s', message='%s', params=%s}", code, message, params);
    }
}
