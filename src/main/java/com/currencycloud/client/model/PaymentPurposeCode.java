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
    private String bankAccountCountry;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getPurposeDescription() {
        return purposeDescription;
    }

    public void setPurposeDescription(String purposeDescription) {
        this.purposeDescription = purposeDescription;
    }

    public String getBankAccountCountry() {
        return bankAccountCountry;
    }

    public void setBankAccountCountry(String bankAccountCountry) {
        this.bankAccountCountry = bankAccountCountry;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("bankAccountCountry", bankAccountCountry)
                .appendField("currency", currency)
                .appendField("entityType", entityType)
                .appendField("purposeCode", purposeCode)
                .appendField("purposeDescription", purposeDescription)
                .toString();
    }
}
