package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.HttpStatusExceptionSupport;
import si.mazi.rescu.InvocationAware;
import si.mazi.rescu.RestInvocation;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ResponseException extends HttpStatusExceptionSupport implements InvocationAware {

    private String errorCode;
    private Map<String, List<ErrorMessage>> errorMessages;
    private RestInvocation invocation;

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
    public String toString() {
        return String.format("CurrencyCloudException{errorCode='%s', errorMessages=%s}", errorCode, errorMessages);
    }

    @Override
    public void setInvocation(RestInvocation invocation) {
        this.invocation = invocation;
    }

    public RestInvocation getInvocation() {
        return invocation;
    }
}
