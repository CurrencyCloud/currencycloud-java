package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("bankAccountHolderName", bankAccountHolderName);
        map.put("beneficiaryAddress", beneficiaryAddress);
        map.put("beneficiaryCountry", beneficiaryCountry);
        map.put("bankName", bankName);
        map.put("bankAddress", bankAddress);
        map.put("bankCountry", bankCountry);
        map.put("currency", currency);
        map.put("bicSwift", bicSwift);
        map.put("iban", iban);
        map.put("accountNumber", accountNumber);
        map.put("routingCodeType1", routingCodeType1);
        map.put("routingCodeValue1", routingCodeValue1);
        map.put("routingCodeType2", routingCodeType2);
        map.put("routingCodeValue2", routingCodeValue2);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
