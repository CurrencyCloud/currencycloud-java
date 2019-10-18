package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentFeeRules {
    @JsonProperty("payment_fee_rules")
    private List<PaymentFeeRule> payment_fee_rules;

    public List<PaymentFeeRule> getPaymentFeeRules() {
        return payment_fee_rules;
    }

    @Override
    public String toString() {
        return String.format("{\"payment_fee_rules\":%s}", payment_fee_rules);
    }
}

