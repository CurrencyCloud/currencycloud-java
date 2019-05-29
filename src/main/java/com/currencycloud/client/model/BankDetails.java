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
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankDetails {

    private String identifierType;
    private String identifierValue;
    private String accountNumber;
    private String bicSwift;
    private String bankName;
    private String bankBranch;
    private String bankAddress;
    private String bankCity;
    private String bankState;
    private String bankPostCode;
    private String bankCountry;
    @JsonProperty("bank_country_ISO")
    private String bankCountryISO;
    private String currency;

    public String getIdentifierValue() {
        return identifierValue;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBicSwift() {
        return bicSwift;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public String getBankCity() {
        return bankCity;
    }

    public String getBankState() {
        return bankState;
    }

    public String getBankPostCode() {
        return bankPostCode;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public String getBankCountryISO() {
        return bankCountryISO;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("identifierValue", identifierValue);
        map.put("identifierType", identifierType);
        map.put("accountNumber", accountNumber);
        map.put("bicSwift", bicSwift);
        map.put("bankName", bankName);
        map.put("bankBranch", bankBranch);
        map.put("bankAddress", bankAddress);
        map.put("bankCity", bankCity);
        map.put("bankState", bankState);
        map.put("bankPostCode", bankPostCode);
        map.put("bankCountry", bankCountry);
        map.put("bankCountryISO", bankCountryISO);
        map.put("currency", currency);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}