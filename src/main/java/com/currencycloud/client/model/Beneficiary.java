package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Beneficiary {

    private String id;

    private String bankAccountHolderName;

    private String name;

    private String email;

    private Boolean defaultBeneficiary;

    private String creatorContactId;

    private Date createdAt;

    private Date updatedAt;

    private List<String> paymentTypes;

    private String bankCountry;

    private String bankName;

    private String currency;

    private String accountNumber;

    @JsonProperty("routing_code_type_1")
    private String routingCodeType1;

    private String bankAccountType;

    private List<String> beneficiaryAddress;

    private String beneficiaryCountry;

    private String beneficiaryEntityType;

    private String beneficiaryCompanyName;

    private String beneficiaryFirstName;

    private String beneficiaryLastName;

    private String beneficiaryCity;

    private String beneficiaryPostcode;

    private String beneficiaryStateOrProvince;

    private String beneficiaryDateOfBirth;

    private String beneficiaryIdentificationType;

    private String beneficiaryIdentificationValue;

    @JsonProperty("routing_code_value_1")
    private String routingCodeValue1;

    @JsonProperty("routing_code_type_2")
    private String routingCodeType2;

    @JsonProperty("routing_code_value_2")
    private String routingCodeValue2;

    private String bicSwift;

    private String iban;

    private List<String> bankAddress;

    public String getId() {
        return id;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getDefaultBeneficiary() {
        return defaultBeneficiary;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<String> getPaymentTypes() {
        return paymentTypes;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingCodeType1() {
        return routingCodeType1;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public List<String> getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public String getBeneficiaryCountry() {
        return beneficiaryCountry;
    }

    public String getBeneficiaryEntityType() {
        return beneficiaryEntityType;
    }

    public String getBeneficiaryCompanyName() {
        return beneficiaryCompanyName;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public String getBeneficiaryCity() {
        return beneficiaryCity;
    }

    public String getBeneficiaryPostcode() {
        return beneficiaryPostcode;
    }

    public String getBeneficiaryStateOrProvince() {
        return beneficiaryStateOrProvince;
    }

    public String getBeneficiaryDateOfBirth() {
        return beneficiaryDateOfBirth;
    }

    public String getBeneficiaryIdentificationType() {
        return beneficiaryIdentificationType;
    }

    public String getBeneficiaryIdentificationValue() {
        return beneficiaryIdentificationValue;
    }

    public String getRoutingCodeValue1() {
        return routingCodeValue1;
    }

    public String getRoutingCodeType2() {
        return routingCodeType2;
    }

    public String getRoutingCodeValue2() {
        return routingCodeValue2;
    }

    public String getBicSwift() {
        return bicSwift;
    }

    public String getIban() {
        return iban;
    }

    public List<String> getBankAddress() {
        return bankAddress;
    }
}

