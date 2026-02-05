package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentFees {

    @JsonProperty("payment_fees")
    private List<PaymentFee> paymentFees;

    public List<PaymentFee> getPaymentFees() {
        return paymentFees;
    }

    public void setPaymentFees(List<PaymentFee> paymentFees) {
        this.paymentFees = paymentFees;
    }

    @Override
    public String toString() {
        return String.format("{\"payment_fees\":%s}", paymentFees);
    }
}
