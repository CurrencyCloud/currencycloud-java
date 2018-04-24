package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Iban implements Entity {

    private String id;
    private String ibanCode;
    private String accountId;
    private String currency;
    private String accountHolderName;
    private String bankInstitutionName;
    private String bankInstitutionAddress;
    private String bankInstitutionCountry;
    private String bicSwift;
    private Date createdAt;
    private Date updatedAt;

    protected Iban() { }

    /**
     * @deprecated as of 1.2.3; IBANs are automatically created upon account creation
     * */
    @Deprecated
    private Iban(String currency) {
        this.currency = currency;
    }

    public static Iban create() {
        return new Iban();
    }

    /**
     * @deprecated as of 1.2.3; IBANs are automatically created upon account creation
     * */
    @Deprecated
    public static Iban create(String currency) {
        return new Iban(currency);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId (String accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getBicSwift() {
        return bicSwift;
    }

    public void setBicSwift(String bicSwift) {
        this.bicSwift = bicSwift;
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
                .appendField("ibanCode", ibanCode)
                .appendField("accountId", accountId)
                .appendField("currency", currency)
                .appendField("accountHolderName", accountHolderName)
                .appendField("bankInstitutionName", bankInstitutionName)
                .appendField("bankInstitutionAddress", bankInstitutionAddress)
                .appendField("bankInstitutionCountry", bankInstitutionCountry)
                .appendField("bicSwift", bicSwift)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
        }
}
