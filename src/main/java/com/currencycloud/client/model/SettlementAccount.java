package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementAccount {

    private String bankAccountHolderName;
    private List<String> beneficiaryAddress = new ArrayList<String>();
    private String beneficiaryCountry;
    private String bankName;
    private List<String> bankAddress = new ArrayList<String>();
    private String bankCountry;
    private String currency;
    private String bicSwift;
    private String iban;
    private String accountNumber;
    @JsonProperty("routing_code_type_1")
    private String routingCodeType1;
    @JsonProperty("routing_code_value_1")
    private String routingCodeValue1;
    @JsonProperty("routing_code_type_2")
    private String routingCodeType2;
    @JsonProperty("routing_code_value_2")
    private String routingCodeValue2;

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public List<String> getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public String getBeneficiaryCountry() {
        return beneficiaryCountry;
    }

    public String getBankName() {
        return bankName;
    }

    public List<String> getBankAddress() {
        return bankAddress;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public String getCurrency() {
        return currency;
    }

    public String getBicSwift() {
        return bicSwift;
    }

    public String getIban() {
        return iban;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingCodeType1() {
        return routingCodeType1;
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

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("bankAccountHolderName", bankAccountHolderName)
                .appendField("beneficiaryAddress", beneficiaryAddress)
                .appendField("beneficiaryCountry", beneficiaryCountry)
                .appendField("bankName", bankName)
                .appendField("bankAddress", bankAddress)
                .appendField("bankCountry", bankCountry)
                .appendField("currency", currency)
                .appendField("bicSwift", bicSwift)
                .appendField("iban", iban)
                .appendField("accountNumber", accountNumber)
                .appendField("routingCodeType1", routingCodeType1)
                .appendField("routingCodeValue1", routingCodeValue1)
                .appendField("routingCodeType2", routingCodeType2)
                .appendField("routingCodeValue2", routingCodeValue2)
                .toString();
    }
}
