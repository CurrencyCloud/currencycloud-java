package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerRequiredDetail {

    private String payerEntityType;
    private String paymentType;
    private List<Map<String, String>> requiredFields = new ArrayList<>();
    private String payerIdentificationType;

    public String getPayerEntityType() {
        return payerEntityType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public List<Map<String, String>> getRequiredFields() {
        return requiredFields;
    }

    public String getPayerIdentificationType() {
        return payerIdentificationType;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("payerEntityType", payerEntityType)
                .appendField("paymentType", paymentType)
                .appendField("requiredFields", requiredFields)
                .appendField("payerIdentificationType", payerIdentificationType)
                .toString();
    }
}
