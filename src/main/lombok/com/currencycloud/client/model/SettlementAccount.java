package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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
