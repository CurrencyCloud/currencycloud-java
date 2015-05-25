
package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payer {

    private String id;
    private String legalEntityType;
    private String companyName;
    private String firstName;
    private String lastName;
    private List<String> address = new ArrayList<>();
    private String city;
    private String stateOrProvince;
    private String country;
    private String identificationType;
    private String identificationValue;
    private String postcode;
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public String getLegalEntityType() {
        return legalEntityType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getAddress() {
        return address;
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

    public String getIdentificationType() {
        return identificationType;
    }

    public String getIdentificationValue() {
        return identificationValue;
    }

    public String getPostcode() {
        return postcode;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format("Payer{id='%s', legalEntityType='%s', companyName='%s', firstName='%s', lastName='%s', address=%s, city='%s', stateOrProvince='%s', country='%s', identificationType='%s', identificationValue='%s', postcode='%s', dateOfBirth=%s, createdAt=%s, updatedAt=%s}",
                id, legalEntityType, companyName, firstName, lastName, address, city, stateOrProvince, country, identificationType, identificationValue, postcode, dateOfBirth, createdAt, updatedAt);
    }
}