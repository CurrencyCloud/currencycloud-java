package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Beneficiary.class)
public class BeneficiaryAccountVerificationRequest implements Entity {
    private String accountNumber;
    private String beneficiaryEntityType;
    private String beneficiaryCompanyName;
    private String beneficiaryFirstName;
    private String beneficiaryLastName;
    private String secondaryReferenceData;
    private String bankCountry;
    private String currency;
    private String iban;
    private String paymentType;

    @JsonProperty("routing_code_type_1")
    private String routingCodeType1;
    @JsonProperty("routing_code_value_1")
    private String routingCodeValue1;

    protected BeneficiaryAccountVerificationRequest() { }

    public static BeneficiaryAccountVerificationRequest create() {
        return new BeneficiaryAccountVerificationRequest();
    }

    @Override
    public String getId() {
        return null;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecondaryReferenceData() {
        return secondaryReferenceData;
    }

    public void setSecondaryReferenceData(String secondaryReferenceData) {
        this.secondaryReferenceData = secondaryReferenceData;
    }

    public String getBeneficiaryEntityType() {
        return beneficiaryEntityType;
    }

    public void setBeneficiaryEntityType(String beneficiaryEntityType) {
        this.beneficiaryEntityType = beneficiaryEntityType;
    }


    public String getBeneficiaryCompanyName() {
        return beneficiaryCompanyName;
    }

    public void setBeneficiaryCompanyName(String beneficiaryCompanyName) {
        this.beneficiaryCompanyName = beneficiaryCompanyName;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getRoutingCodeType1() {
        return routingCodeType1;
    }

    public void setRoutingCodeType1(String routingCodeType1) {
        this.routingCodeType1 = routingCodeType1;
    }

    public String getRoutingCodeValue1() {
        return routingCodeValue1;
    }

    public void setRoutingCodeValue1(String routingCodeValue1) {
        this.routingCodeValue1 = routingCodeValue1;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("accountNumber", accountNumber);
        map.put("routingCodeType1", routingCodeType1);
        map.put("beneficiaryCompanyName", beneficiaryCompanyName);
        map.put("beneficiaryFirstName", beneficiaryFirstName);
        map.put("beneficiaryLastName", beneficiaryLastName);
        map.put("routingCodeValue1", routingCodeValue1);
        map.put("secondaryReferenceData", secondaryReferenceData);
        map.put("beneficiaryEntityType", beneficiaryEntityType);
        map.put("bankCountry", bankCountry);
        map.put("currency", currency);
        map.put("iban", iban);
        map.put("paymentType", paymentType);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}

