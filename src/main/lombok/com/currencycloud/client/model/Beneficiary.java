package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minidev.json.JSONObject;

import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Beneficiary.class)
@Data
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
    private String scope;
    private String beneficiaryExternalReference;

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

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("bankAccountHolderName", bankAccountHolderName)
                .appendField("name", name)
                .appendField("email", email)
                .appendField("defaultBeneficiary", defaultBeneficiary)
                .appendField("creatorContactId", creatorContactId)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .appendField("paymentTypes", paymentTypes)
                .appendField("bankCountry", bankCountry)
                .appendField("bankName", bankName)
                .appendField("currency", currency)
                .appendField("accountNumber", accountNumber)
                .appendField("routingCodeType1", routingCodeType1)
                .appendField("bankAccountType", bankAccountType)
                .appendField("beneficiaryAddress", beneficiaryAddress)
                .appendField("beneficiaryCountry", beneficiaryCountry)
                .appendField("beneficiaryEntityType", beneficiaryEntityType)
                .appendField("beneficiaryCompanyName", beneficiaryCompanyName)
                .appendField("beneficiaryFirstName", beneficiaryFirstName)
                .appendField("beneficiaryLastName", beneficiaryLastName)
                .appendField("beneficiaryCity", beneficiaryCity)
                .appendField("beneficiaryPostcode", beneficiaryPostcode)
                .appendField("beneficiaryStateOrProvince", beneficiaryStateOrProvince)
                .appendField("beneficiaryDateOfBirth", beneficiaryDateOfBirth)
                .appendField("beneficiaryIdentificationType", beneficiaryIdentificationType)
                .appendField("beneficiaryIdentificationValue", beneficiaryIdentificationValue)
                .appendField("routingCodeValue1", routingCodeValue1)
                .appendField("routingCodeType2", routingCodeType2)
                .appendField("routingCodeValue2", routingCodeValue2)
                .appendField("bicSwift", bicSwift)
                .appendField("iban", iban)
                .appendField("bankAddress", bankAddress)
                .appendField("beneficiaryExternalReference", beneficiaryExternalReference)
                .toString();
    }
}

