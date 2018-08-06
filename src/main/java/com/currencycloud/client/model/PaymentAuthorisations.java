package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentAuthorisations {
    @JsonProperty("authorisations")
    private List<PaymentAuthorisation> paymentAuthorisations;

    public List<PaymentAuthorisation> getPaymentAuthorisations() {
        return paymentAuthorisations;
    }

    @Override
    public String toString() {
        return String.format("{\"authorisations\":%s}", paymentAuthorisations);
    }
}
