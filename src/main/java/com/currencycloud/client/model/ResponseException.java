package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.HttpResponseAware;
import si.mazi.rescu.HttpStatusExceptionSupport;
import si.mazi.rescu.InvocationAware;
import si.mazi.rescu.RestInvocation;

import java.util.List;
import java.util.Map;

/**
 * ResponseException instances are created and populated by the rescu library: When the HTTP response code
 * differs from 200, the response body json is interpreted as a ResponseException.
 *
 * Note that this works because ResponseException is declared on the HTTP API interface methods
 * ({@link com.currencycloud.client.CurrencyCloud}), and it is mapped to json using Jackson annotations.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseException extends HttpStatusExceptionSupport implements InvocationAware, HttpResponseAware {

    private String errorCode;
    private Map<String, List<ErrorMessage>> errorMessages;
    private RestInvocation invocation;
    private Map<String, List<String>> headers;

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * A map of error messages where the key is the field/parameter whose value cause the error, and the value
     * contains the error message details.
     *
     * @see ErrorMessage
     */
    public Map<String, List<ErrorMessage>> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public void setInvocation(RestInvocation invocation) {
        this.invocation = invocation;
    }

    public RestInvocation getInvocation() {
        return invocation;
    }

    @Override
    public void setResponseHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return String.format("ResponseException{errorCode='%s', errorMessages=%s}", errorCode, errorMessages);
    }
}
