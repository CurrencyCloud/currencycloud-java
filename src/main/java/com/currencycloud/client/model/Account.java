package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

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

    public String getId() {
        return id;
    }

    public String getLegalEntityType() {
        return legalEntityType;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getBrand() {
        return brand;
    }

    public String getYourReference() {
        return yourReference;
    }

    public String getStatus() {
        return status;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getSpreadTable() {
        return spreadTable;
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

    public String getIdentificationValue() {
        return identificationValue;
    }

    public String getShortReference() {
        return shortReference;
    }

    @Override
    public String toString() {
        return String.format("Account{id='%s', legalEntityType='%s', accountName='%s', brand='%s', yourReference='%s', status='%s', street='%s', city='%s', stateOrProvince='%s', country='%s', postalCode='%s', spreadTable='%s', createdAt=%s, updatedAt=%s, identificationType='%s', identificationValue='%s', shortReference='%s'}",
                id, legalEntityType, accountName, brand, yourReference, status, street, city, stateOrProvince, country, postalCode, spreadTable, createdAt, updatedAt, identificationType, identificationValue, shortReference);
    }
}
