package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Account.class)
public class Account implements Entity {

    private String id;  
    private String legalEntityType;
    private String accountName;
    private String brand;
    private String yourReference;
    private String status;
    private String street;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private String spreadTable;
    private Date createdAt;
    private Date updatedAt;
    private String identificationType;
    private String identificationValue;
    private String shortReference;
    private Boolean apiTrading;
    private Boolean onlineTrading;
    private Boolean phoneTrading;
    protected Account() { }

    private Account(String accountName, String legalEntityType, String street, String city, String postalCode, String country) {
        this.accountName = accountName;
        this.legalEntityType = legalEntityType;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public static Account create(String accountName, String legalEntityType, String street, String city, String postalCode, String country) {
        return new Account(accountName, legalEntityType, street, city, postalCode, country);
    }

    public static Account create() {
        return new Account();
    }

    public String getId() {
        return id;
    }

    public String getLegalEntityType() {
        return legalEntityType;
    }

    public void setLegalEntityType(String legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYourReference() {
        return yourReference;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSpreadTable() {
        return spreadTable;
    }

    public void setSpreadTable(String spreadTable) {
        this.spreadTable = spreadTable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationValue() {
        return identificationValue;
    }

    public void setIdentificationValue(String identificationValue) {
        this.identificationValue = identificationValue;
    }

    public String getShortReference() {
        return shortReference;
    }

    public void setShortReference(String shortReference) {
        this.shortReference = shortReference;
    }

    public Boolean getApiTrading() {
        return apiTrading;
    }

    public void setApiTrading(Boolean apiTrading) {
        this.apiTrading = apiTrading;
    }

    public Boolean getOnlineTrading() {
        return onlineTrading;
    }

    public void setOnlineTrading(Boolean onlineTrading) {
        this.onlineTrading = onlineTrading;
    }

    public Boolean getPhoneTrading() {
        return phoneTrading;
    }

    public void setPhoneTrading(Boolean phoneTrading) {
        this.phoneTrading = phoneTrading;
    }

    @Override
    public String toString() {
        return String.format("Account{id='%s', legalEntityType='%s', accountName='%s', brand='%s', yourReference='%s', status='%s', street='%s', city='%s', stateOrProvince='%s', country='%s', postalCode='%s', spreadTable='%s', createdAt=%s, updatedAt=%s, identificationType='%s', identificationValue='%s', shortReference='%s', apiTrading='%s', onlineTrading='%s', phoneTrading='%s'}",
                id, legalEntityType, accountName, brand, yourReference, status, street, city, stateOrProvince, country, postalCode, spreadTable, createdAt, updatedAt, identificationType, identificationValue, shortReference, apiTrading, onlineTrading, phoneTrading);
    }
}
