package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Beneficiary.class)
public class Beneficiary implements Entity {

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
    private Date beneficiaryDateOfBirth;
    private String beneficiaryIdentificationType;
    private String beneficiaryIdentificationValue;

    @JsonProperty("routing_code_type_1")
    private String routingCodeType1;
    @JsonProperty("routing_code_value_1")
    private String routingCodeValue1;
    @JsonProperty("routing_code_type_2")
    private String routingCodeType2;
    @JsonProperty("routing_code_value_2")
    private String routingCodeValue2;

    private String bicSwift;
    private String iban;
    private List<String> bankAddress;

    protected Beneficiary() { }

    private Beneficiary(String id) {
        this.id = id;
    }

    private Beneficiary(String bankCountry, String currency, String beneficiaryCountry) {
        this.bankCountry = bankCountry;
        this.currency = currency;
        this.beneficiaryCountry = beneficiaryCountry;
    }

    private Beneficiary(String bankAccountHolderName, String bankCountry, String currency, String name) {
        this.bankAccountHolderName = bankAccountHolderName;
        this.bankCountry = bankCountry;
        this.currency = currency;
        this.name = name;
    }

    public static Beneficiary create() {
        return new Beneficiary();
    }

    /**
     * Creates a Beneficiary with all the required properties for the update beneficiaries method. Note that this
     * is just a simple helper factory matedod and can be used for any other purpose.
     * @deprecated  todo: This is only used in tests and shoud not be part of the public API
     */
    @Deprecated
    public static Beneficiary createForUpdate(String id) {
        return new Beneficiary(id);
    }

    /**
     * Creates a Beneficiary with all the required properties for the validate beneficiary method. Note that this
     * is just a simple helper factory matedod and can be used for any other purpose.
     */
    public static Beneficiary createForValidate(String bankCountry, String currency, String beneficiaryCountry) {
        return new Beneficiary(bankCountry, currency, beneficiaryCountry);
    }

    /**
     * Creates a Beneficiary with all the required properties for the create beneficiary method. Note that this
     * is just a simple helper factory matedod and can be used for any other purpose.
     */
    public static Beneficiary create(String bankAccountHolderName, String bankCountry, String currency, String name) {
        return new Beneficiary(bankAccountHolderName, bankCountry, currency, name);
    }

    public String getId() {
        return id;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public void setBankAccountHolderName(String bankAccountHolderName) {
        this.bankAccountHolderName = bankAccountHolderName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDefaultBeneficiary() {
        return defaultBeneficiary;
    }

    public void setDefaultBeneficiary(Boolean defaultBeneficiary) {
        this.defaultBeneficiary = defaultBeneficiary;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public void setCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
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

    public void setPaymentTypes(List<String> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingCodeType1() {
        return routingCodeType1;
    }

    public void setRoutingCodeType1(String routingCodeType1) {
        this.routingCodeType1 = routingCodeType1;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public List<String> getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(List<String> beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    public String getBeneficiaryCountry() {
        return beneficiaryCountry;
    }

    public void setBeneficiaryCountry(String beneficiaryCountry) {
        this.beneficiaryCountry = beneficiaryCountry;
    }

    public String getBeneficiaryEntityType() {
        return beneficiaryEntityType;
    }

    public void setBeneficiaryEntityType(String beneficiaryEntityType) {
        this.beneficiaryEntityType = beneficiaryEntityType;
    }

    public String getBeneficiaryCompanyName() {
        return beneficiaryCompanyName;
    }

    public void setBeneficiaryCompanyName(String beneficiaryCompanyName) {
        this.beneficiaryCompanyName = beneficiaryCompanyName;
    }

    public String getBeneficiaryFirstName() {
        return beneficiaryFirstName;
    }

    public void setBeneficiaryFirstName(String beneficiaryFirstName) {
        this.beneficiaryFirstName = beneficiaryFirstName;
    }

    public String getBeneficiaryLastName() {
        return beneficiaryLastName;
    }

    public void setBeneficiaryLastName(String beneficiaryLastName) {
        this.beneficiaryLastName = beneficiaryLastName;
    }

    public String getBeneficiaryCity() {
        return beneficiaryCity;
    }

    public void setBeneficiaryCity(String beneficiaryCity) {
        this.beneficiaryCity = beneficiaryCity;
    }

    public String getBeneficiaryPostcode() {
        return beneficiaryPostcode;
    }

    public void setBeneficiaryPostcode(String beneficiaryPostcode) {
        this.beneficiaryPostcode = beneficiaryPostcode;
    }

    public String getBeneficiaryStateOrProvince() {
        return beneficiaryStateOrProvince;
    }

    public void setBeneficiaryStateOrProvince(String beneficiaryStateOrProvince) {
        this.beneficiaryStateOrProvince = beneficiaryStateOrProvince;
    }

    public Date getBeneficiaryDateOfBirth() {
        return beneficiaryDateOfBirth;
    }

    public void setBeneficiaryDateOfBirth(Date beneficiaryDateOfBirth) {
        this.beneficiaryDateOfBirth = beneficiaryDateOfBirth;
    }

    public String getBeneficiaryIdentificationType() {
        return beneficiaryIdentificationType;
    }

    public void setBeneficiaryIdentificationType(String beneficiaryIdentificationType) {
        this.beneficiaryIdentificationType = beneficiaryIdentificationType;
    }

    public String getBeneficiaryIdentificationValue() {
        return beneficiaryIdentificationValue;
    }

    public void setBeneficiaryIdentificationValue(String beneficiaryIdentificationValue) {
        this.beneficiaryIdentificationValue = beneficiaryIdentificationValue;
    }

    public String getRoutingCodeValue1() {
        return routingCodeValue1;
    }

    public void setRoutingCodeValue1(String routingCodeValue1) {
        this.routingCodeValue1 = routingCodeValue1;
    }

    public String getRoutingCodeType2() {
        return routingCodeType2;
    }

    public void setRoutingCodeType2(String routingCodeType2) {
        this.routingCodeType2 = routingCodeType2;
    }

    public String getRoutingCodeValue2() {
        return routingCodeValue2;
    }

    public void setRoutingCodeValue2(String routingCodeValue2) {
        this.routingCodeValue2 = routingCodeValue2;
    }

    public String getBicSwift() {
        return bicSwift;
    }

    public void setBicSwift(String bicSwift) {
        this.bicSwift = bicSwift;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<String> getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(List<String> bankAddress) {
        this.bankAddress = bankAddress;
    }

    @Override
    public String toString() {
        return String.format("Beneficiary{id='%s', bankAccountHolderName='%s', name='%s', email='%s', defaultBeneficiary=%s, creatorContactId='%s', createdAt=%s, updatedAt=%s, paymentTypes=%s, bankCountry='%s', bankName='%s', currency='%s', accountNumber='%s', routingCodeType1='%s', bankAccountType='%s', beneficiaryAddress=%s, beneficiaryCountry='%s', beneficiaryEntityType='%s', beneficiaryCompanyName='%s', beneficiaryFirstName='%s', beneficiaryLastName='%s', beneficiaryCity='%s', beneficiaryPostcode='%s', beneficiaryStateOrProvince='%s', beneficiaryDateOfBirth='%s', beneficiaryIdentificationType='%s', beneficiaryIdentificationValue='%s', routingCodeValue1='%s', routingCodeType2='%s', routingCodeValue2='%s', bicSwift='%s', iban='%s', bankAddress=%s}",
                id, bankAccountHolderName, name, email, defaultBeneficiary, creatorContactId, createdAt, updatedAt, paymentTypes, bankCountry, bankName, currency, accountNumber, routingCodeType1, bankAccountType, beneficiaryAddress, beneficiaryCountry, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, beneficiaryIdentificationType, beneficiaryIdentificationValue, routingCodeValue1, routingCodeType2, routingCodeValue2, bicSwift, iban, bankAddress);
    }
}

