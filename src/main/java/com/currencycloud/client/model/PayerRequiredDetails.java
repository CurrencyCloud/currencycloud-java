package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerRequiredDetails {

    @JsonProperty("details")
    private List<PayerRequiredDetail> payerRequiredDetails;

    public List<PayerRequiredDetail> getPayerRequiredDetails() {
        return payerRequiredDetails;
    }

    @Override
    public String toString() {
        return String.format("{\"details\":%s}", payerRequiredDetails);
    }
}
