package com.currencycloud.client.model;

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
public class VirtualAccount implements Entity {

    private String id;
    private String accountId;
    private String virtualAccountNumber;
    private String accountHolderName;
    private String bankInstitutionName;
    private String bankInstitutionAddress;
    private String bankInstitutionCountry;
    private String routingCode;
    private Date createdAt;
    private Date updatedAt;
    private String scope;

    protected VirtualAccount() { }

    public static VirtualAccount create() {
        return new VirtualAccount();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVirtualAccountNumber() {
        return virtualAccountNumber;
    }

    public void setVirtualAccountNumber(String virtualAccountNumber) {
        this.virtualAccountNumber = virtualAccountNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId (String accountId) {
        this.accountId = accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankInstitutionName() {
        return bankInstitutionName;
    }

    public void setBankInstitutionName(String bankInstitutionName) {
        this.bankInstitutionName = bankInstitutionName;
    }

    public String getBankInstitutionAddress() {
        return bankInstitutionAddress;
    }

    public void setBankInstitutionAddress (String bankInstitutionAddress) {
        this.bankInstitutionAddress = bankInstitutionAddress;
    }

    public String getBankInstitutionCountry() {
        return bankInstitutionCountry;
    }

    public void setBankInstitutionCountry(String bankInstitutionCountry) {
        this.bankInstitutionCountry = bankInstitutionCountry;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("accountId", accountId);
        map.put("virtualAccountNumber", virtualAccountNumber);
        map.put("accountHolderName", accountHolderName);
        map.put("bankInstitutionName", bankInstitutionName);
        map.put("bankInstitutionAddress", bankInstitutionAddress);
        map.put("bankInstitutionCountry", bankInstitutionCountry);
        map.put("routingCode", routingCode);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
