package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Iban implements Entity {

    private String uuid;
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

    protected Iban() {}

    private Iban(String currency) {
        this.currency = currency;
    }

    public static Iban create() {
        return new Iban();
    }

    /**
     * Creates an IBAN as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createIban(Iban)} method,
     */
    public static Iban create(String currency) {
        return new Iban(currency);
    }

    @Override
    public String getId() {
        return uuid;
    }

    public String getUuid() {
        return uuid;
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

    public void setGetBicSwift(String bicSwift) {
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
        return String.format("Iban{uuid='%s', ibanCode='%s', accountId='%s', currency='%s', accountHolderName='%s', bankInstitutionName='%s', bankInstitutionAddress='%s', bankInstitutionCountry='%s', bicSwift='%s', createdAt=%s, updatedAt=%s}",
                uuid, ibanCode, accountId, currency, accountHolderName, bankInstitutionName, bankInstitutionAddress, bankInstitutionCountry, bicSwift, createdAt, updatedAt);
    }
}
