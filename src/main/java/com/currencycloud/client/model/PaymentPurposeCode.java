package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentPurposeCode {

    private String currency;
    private String entityType;
    private String purposeCode;
    private String purposeDescription;

    public String getCurrency() {
        return currency;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public String getPurposeDescription() {
        return purposeDescription;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("currency", currency)
                .appendField("entityType", entityType)
                .appendField("purposeCode", purposeCode)
                .appendField("purposeDescription", purposeDescription)
                .toString();
    }
}
