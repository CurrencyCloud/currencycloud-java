package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.Date;

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

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("accountId", accountId)
                .appendField("virtualAccountNumber", virtualAccountNumber)
                .appendField("accountHolderName", accountHolderName)
                .appendField("bankInstitutionName", bankInstitutionName)
                .appendField("bankInstitutionAddress", bankInstitutionAddress)
                .appendField("bankInstitutionCountry", bankInstitutionCountry)
                .appendField("routingCode", routingCode)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
        }
}
