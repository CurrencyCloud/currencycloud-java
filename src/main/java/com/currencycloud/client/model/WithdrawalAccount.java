package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawalAccount  implements Entity {

    private String id;
    private String accountName;
    private String accountHolderName;
    private Date accountHolderDob;
    private String routingCode;
    private String accountNumber;
    private String currency;
    private String accountId;

    protected WithdrawalAccount() { }

    public static WithdrawalAccount create() {
        return new WithdrawalAccount();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Date getAccountHolderDOB() {
        return accountHolderDob;
    }

    public void setAccountHolderDOB(Date accountHolderDob) {
        this.accountHolderDob = accountHolderDob;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber (String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("accountName", accountName);
        map.put("accountHolderName", accountHolderName);
        map.put("accountHolderDob", accountHolderDob);
        map.put("routingCode", routingCode);
        map.put("accountNumber", accountNumber);
        map.put("currency", currency);
        map.put("accountId", accountId);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}