package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountComplianceSettings {

    private String accountId;
    private String industryType;
    private String countryOfIncorporation;
    private Date dateOfIncorporation;
    private String businessWebsiteUrl;
    private List<String> expectedTransactionCountries;
    private List<String> expectedTransactionCurrencies;
    private Integer expectedMonthlyActivityVolume;
    private BigDecimal expectedMonthlyActivityValue;
    private String taxIdentification;
    private String nationalIdentification;
    private String countryOfCitizenship;
    private String tradingAddressStreet;
    private String tradingAddressCity;
    private String tradingAddressState;
    private String tradingAddressPostalcode;
    private String tradingAddressCountry;
    private String customerRisk;

    public AccountComplianceSettings() {
    }

    public static AccountComplianceSettings create() {
        return new AccountComplianceSettings();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getCountryOfIncorporation() {
        return countryOfIncorporation;
    }

    public void setCountryOfIncorporation(String countryOfIncorporation) {
        this.countryOfIncorporation = countryOfIncorporation;
    }

    public Date getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(Date dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getBusinessWebsiteUrl() {
        return businessWebsiteUrl;
    }

    public void setBusinessWebsiteUrl(String businessWebsiteUrl) {
        this.businessWebsiteUrl = businessWebsiteUrl;
    }

    public List<String> getExpectedTransactionCountries() {
        return expectedTransactionCountries;
    }

    public void setExpectedTransactionCountries(List<String> expectedTransactionCountries) {
        this.expectedTransactionCountries = expectedTransactionCountries;
    }

    public List<String> getExpectedTransactionCurrencies() {
        return expectedTransactionCurrencies;
    }

    public void setExpectedTransactionCurrencies(List<String> expectedTransactionCurrencies) {
        this.expectedTransactionCurrencies = expectedTransactionCurrencies;
    }

    public Integer getExpectedMonthlyActivityVolume() {
        return expectedMonthlyActivityVolume;
    }

    public void setExpectedMonthlyActivityVolume(Integer expectedMonthlyActivityVolume) {
        this.expectedMonthlyActivityVolume = expectedMonthlyActivityVolume;
    }

    public BigDecimal getExpectedMonthlyActivityValue() {
        return expectedMonthlyActivityValue;
    }

    public void setExpectedMonthlyActivityValue(BigDecimal expectedMonthlyActivityValue) {
        this.expectedMonthlyActivityValue = expectedMonthlyActivityValue;
    }

    public String getTaxIdentification() {
        return taxIdentification;
    }

    public void setTaxIdentification(String taxIdentification) {
        this.taxIdentification = taxIdentification;
    }

    public String getNationalIdentification() {
        return nationalIdentification;
    }

    public void setNationalIdentification(String nationalIdentification) {
        this.nationalIdentification = nationalIdentification;
    }

    public String getCountryOfCitizenship() {
        return countryOfCitizenship;
    }

    public void setCountryOfCitizenship(String countryOfCitizenship) {
        this.countryOfCitizenship = countryOfCitizenship;
    }

    public String getTradingAddressStreet() {
        return tradingAddressStreet;
    }

    public void setTradingAddressStreet(String tradingAddressStreet) {
        this.tradingAddressStreet = tradingAddressStreet;
    }

    public String getTradingAddressCity() {
        return tradingAddressCity;
    }

    public void setTradingAddressCity(String tradingAddressCity) {
        this.tradingAddressCity = tradingAddressCity;
    }

    public String getTradingAddressState() {
        return tradingAddressState;
    }

    public void setTradingAddressState(String tradingAddressState) {
        this.tradingAddressState = tradingAddressState;
    }

    public String getTradingAddressPostalcode() {
        return tradingAddressPostalcode;
    }

    public void setTradingAddressPostalcode(String tradingAddressPostalcode) {
        this.tradingAddressPostalcode = tradingAddressPostalcode;
    }

    public String getTradingAddressCountry() {
        return tradingAddressCountry;
    }

    public void setTradingAddressCountry(String tradingAddressCountry) {
        this.tradingAddressCountry = tradingAddressCountry;
    }

    public String getCustomerRisk() {
        return customerRisk;
    }

    public void setCustomerRisk(String customerRisk) {
        this.customerRisk = customerRisk;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("industryType", industryType);
        map.put("countryOfIncorporation", countryOfIncorporation);
        map.put("dateOfIncorporation", dateOfIncorporation);
        map.put("businessWebsiteUrl", businessWebsiteUrl);
        map.put("expectedTransactionCountries", expectedTransactionCountries);
        map.put("expectedTransactionCurrencies", expectedTransactionCurrencies);
        map.put("expectedMonthlyActivityVolume", expectedMonthlyActivityVolume);
        map.put("expectedMonthlyActivityValue", expectedMonthlyActivityValue);
        map.put("taxIdentification", taxIdentification);
        map.put("nationalIdentification", nationalIdentification);
        map.put("countryOfCitizenship", countryOfCitizenship);
        map.put("tradingAddressStreet", tradingAddressStreet);
        map.put("tradingAddressCity", tradingAddressCity);
        map.put("tradingAddressState", tradingAddressState);
        map.put("tradingAddressPostalcode", tradingAddressPostalcode);
        map.put("tradingAddressCountry", tradingAddressCountry);
        map.put("customerRisk", customerRisk);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}