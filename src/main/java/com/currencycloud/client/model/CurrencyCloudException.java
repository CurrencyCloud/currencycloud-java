package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.HttpStatusExceptionSupport;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CurrencyCloudException extends HttpStatusExceptionSupport {

    private String errorCode;
    private Map<String, List<ErrorMessage>> errorMessages;

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, List<ErrorMessage>> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public String toString() {
        return String.format("CurrencyCloudException{errorCode='%s', errorMessages=%s}", errorCode, errorMessages);
    }
}
