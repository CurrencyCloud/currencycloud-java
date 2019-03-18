package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountPaymentChargesSettings {

    @JsonProperty("payment_charges_settings")
    private List<AccountPaymentChargesSetting> paymentChargesSettings;

    public List<AccountPaymentChargesSetting> getPaymentChargesSettings() {
        return paymentChargesSettings;
    }

    @Override
    public String toString() {
        return String.format("{\"payment_charges_settings\":%s}", paymentChargesSettings);
    }
}
