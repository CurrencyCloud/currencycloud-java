package com.currencycloud.client.model;

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
    private Boolean processThirdPartyFunds;
    private String settlementType;

    protected Account() { }

    private Account(String accountName, String legalEntityType, String street, String city, String postalCode, String country) {
        this.accountName = accountName;
        this.legalEntityType = legalEntityType;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public static Account create() {
        return new Account();
    }

    public static Account create(String accountName, String legalEntityType, String street, String city, String postalCode, String country) {
        return new Account(accountName, legalEntityType, street, city, postalCode, country);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getProcessThirdPartyFunds() {
        return processThirdPartyFunds;
    }

    public void setProcessThirdPartyFunds(Boolean processThirdPartyFunds) {
        this.processThirdPartyFunds = processThirdPartyFunds;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("legalEntityType", legalEntityType);
        map.put("accountName", accountName);
        map.put("brand", brand);
        map.put("yourReference", yourReference);
        map.put("status", status);
        map.put("street", street);
        map.put("city", city);
        map.put("stateOrProvince", stateOrProvince);
        map.put("country", country);
        map.put("postalCode", postalCode);
        map.put("spreadTable", spreadTable);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("identificationType", identificationType);
        map.put("identificationValue", identificationValue);
        map.put("shortReference", shortReference);
        map.put("apiTrading", apiTrading);
        map.put("onlineTrading", onlineTrading);
        map.put("phoneTrading", phoneTrading);
        map.put("processThirdPartyFunds", processThirdPartyFunds);
        map.put("settlementType", settlementType);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
