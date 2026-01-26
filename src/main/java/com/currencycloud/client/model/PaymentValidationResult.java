package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.HttpResponseAware;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentValidationResult implements HttpResponseAware {

    private static final String X_SCA_REQUIRED = "x-sca-required";
    private static final String X_SCA_ID = "x-sca-id";
    private static final String X_SCA_TYPE = "x-sca-type";
    private static final Logger log = LoggerFactory.getLogger(PaymentValidationResult.class);

    private String validationResult;
    private Map<String, List<String>> responseHeaders;


    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }


    @Override
    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public boolean isSCARequired() {
        return Boolean.parseBoolean(getHeaderValue(X_SCA_REQUIRED));
    }

    public String getScaId() {
        return getHeaderValue(X_SCA_ID);
    }

    public String getScaType() {
        return getHeaderValue(X_SCA_TYPE);
    }

    private String getHeaderValue(final String key) {
        if (responseHeaders != null && responseHeaders.containsKey(key)) {
            List<String> values = responseHeaders.get(key);
            if(values != null) {
                if (values.size() == 1) {
                    return values.get(0);
                }
                else if (values.size() > 1) {
                    final String msg = "Multiple values found for header \""+key+"\"";
                    log.warn(msg);
                }
            }
        }
        return null;
    }
}
