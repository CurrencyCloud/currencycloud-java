package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundingAccount implements Entity {

    private String id;
    private String accountId;
    private String accountNumber;
    private String accountNumberType;
    private String accountHolderName;
    private String bankName;
    private String bankAddress;
    private String bankCountry;
    private String currency;
    private String paymentType;
    private String regularRoutingCode;
    private String regularRoutingCodeType;
    private String priorityRoutingCode;
    private String priorityRoutingCodeType;
    private Date createdAt;
    private Date updatedAt;

    protected FundingAccount() { }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumberType() {
        return accountNumberType;
    }

    public void setAccountNumberType(String accountNumberType) {
        this.accountNumberType = accountNumberType;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRegularRoutingCode() {
        return regularRoutingCode;
    }

    public void setRegularRoutingCode(String regularRoutingCode) {
        this.regularRoutingCode = regularRoutingCode;
    }

    public String getRegularRoutingCodeType() {
        return regularRoutingCodeType;
    }

    public void setRegularRoutingCodeType(String regularRoutingCodeType) {
        this.regularRoutingCodeType = regularRoutingCodeType;
    }

    public String getPriorityRoutingCode() {
        return priorityRoutingCode;
    }

    public void setPriorityRoutingCode(String priorityRoutingCode) {
        this.priorityRoutingCode = priorityRoutingCode;
    }

    public String getPriorityRoutingCodeType() {
        return priorityRoutingCodeType;
    }

    public void setPriorityRoutingCodeType(String priorityRoutingCodeType) {
        this.priorityRoutingCodeType = priorityRoutingCodeType;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("accountId", accountId);
        map.put("accountNumber", accountNumber);
        map.put("accountNumberTYpe", accountNumberType);
        map.put("accountHolderName", accountHolderName);
        map.put("bankName", bankName);
        map.put("bankAddress", bankAddress);
        map.put("bankCountry", bankCountry);
        map.put("currency", currency);
        map.put("paymentType", paymentType);
        map.put("regularRoutingCode", regularRoutingCode);
        map.put("regularRoutingCodeType", regularRoutingCodeType);
        map.put("priorityRoutingCode", priorityRoutingCode);
        map.put("priorityRoutingCodeType", priorityRoutingCodeType);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
