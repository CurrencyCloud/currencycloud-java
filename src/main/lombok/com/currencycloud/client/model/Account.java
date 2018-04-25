package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Account.class)
@Data
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

    protected Account() {
    }

    private Account(String accountName, String legalEntityType, String street, String city, String country) {
        this.accountName = accountName;
        this.legalEntityType = legalEntityType;
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public static Account create(String accountName, String legalEntityType, String street, String city, String country) {
        return new Account(accountName, legalEntityType, street, city, country);
    }

    public static Account create(String accountName, String legalEntityType) {
        return new Account(accountName, legalEntityType, null, null, null);
    }

    public static Account create() {
        return new Account();
    }
}
