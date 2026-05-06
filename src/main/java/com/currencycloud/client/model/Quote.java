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
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Quote implements Entity {

    private String quoteId;
    private String buyCurrency;
    private String sellCurrency;
    private String fixedSide;
    private BigDecimal amount;
    private String holdPeriod;
    private Date conversionDate;
    private String conversionDatePreference;
    private BigDecimal clientBuyAmount;
    private BigDecimal clientSellAmount;
    private BigDecimal clientRate;
    private BigDecimal partnerBuyAmount;
    private BigDecimal partnerSellAmount;
    private BigDecimal partnerRate;
    private BigDecimal coreRate;
    private BigDecimal midMarketRate;
    private String currencyPair;
    private Boolean depositRequired;
    private BigDecimal depositAmount;
    private String depositCurrency;
    private Date createdAt;
    private Date expiresAt;
    private Date settlementCutOffTime;

    protected Quote() { }

    private Quote(
            String buyCurrency,
            String sellCurrency,
            String fixedSide,
            BigDecimal amount,
            String holdPeriod
    ) {
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.fixedSide = fixedSide;
        this.amount = amount;
        this.holdPeriod = holdPeriod;
    }

    public static Quote create() {
        return new Quote();
    }

    /** Creates a Quote with only the required properties for creation. */
    public static Quote create(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, String holdPeriod) {
        return new Quote(buyCurrency, sellCurrency, fixedSide, amount, holdPeriod);
    }

    @Override
    public String getId() {
        return quoteId;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public String getFixedSide() {
        return fixedSide;
    }

    public void setFixedSide(String fixedSide) {
        this.fixedSide = fixedSide;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHoldPeriod() {
        return holdPeriod;
    }

    public void setHoldPeriod(String holdPeriod) {
        this.holdPeriod = holdPeriod;
    }

    public Date getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }

    public String getConversionDatePreference() {
        return conversionDatePreference;
    }

    public void setConversionDatePreference(String conversionDatePreference) {
        this.conversionDatePreference = conversionDatePreference;
    }

    public BigDecimal getClientBuyAmount() {
        return clientBuyAmount;
    }

    public void setClientBuyAmount(BigDecimal clientBuyAmount) {
        this.clientBuyAmount = clientBuyAmount;
    }

    public BigDecimal getClientSellAmount() {
        return clientSellAmount;
    }

    public void setClientSellAmount(BigDecimal clientSellAmount) {
        this.clientSellAmount = clientSellAmount;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    public BigDecimal getPartnerBuyAmount() {
        return partnerBuyAmount;
    }

    public void setPartnerBuyAmount(BigDecimal partnerBuyAmount) {
        this.partnerBuyAmount = partnerBuyAmount;
    }

    public BigDecimal getPartnerSellAmount() {
        return partnerSellAmount;
    }

    public void setPartnerSellAmount(BigDecimal partnerSellAmount) {
        this.partnerSellAmount = partnerSellAmount;
    }

    public BigDecimal getPartnerRate() {
        return partnerRate;
    }

    public void setPartnerRate(BigDecimal partnerRate) {
        this.partnerRate = partnerRate;
    }

    public BigDecimal getCoreRate() {
        return coreRate;
    }

    public void setCoreRate(BigDecimal coreRate) {
        this.coreRate = coreRate;
    }

    public BigDecimal getMidMarketRate() {
        return midMarketRate;
    }

    public void setMidMarketRate(BigDecimal midMarketRate) {
        this.midMarketRate = midMarketRate;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Boolean getDepositRequired() {
        return depositRequired;
    }

    public void setDepositRequired(Boolean depositRequired) {
        this.depositRequired = depositRequired;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositCurrency() {
        return depositCurrency;
    }

    public void setDepositCurrency(String depositCurrency) {
        this.depositCurrency = depositCurrency;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getSettlementCutOffTime() {
        return settlementCutOffTime;
    }

    public void setSettlementCutOffTime(Date settlementCutOffTime) {
        this.settlementCutOffTime = settlementCutOffTime;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("quoteId", quoteId);
        map.put("buyCurrency", buyCurrency);
        map.put("sellCurrency", sellCurrency);
        map.put("fixedSide", fixedSide);
        map.put("amount", amount);
        map.put("holdPeriod", holdPeriod);
        map.put("conversionDate", conversionDate);
        map.put("conversionDatePreference", conversionDatePreference);
        map.put("clientBuyAmount", clientBuyAmount);
        map.put("clientSellAmount", clientSellAmount);
        map.put("clientRate", clientRate);
        map.put("partnerBuyAmount", partnerBuyAmount);
        map.put("partnerSellAmount", partnerSellAmount);
        map.put("partnerRate", partnerRate);
        map.put("coreRate", coreRate);
        map.put("midMarketRate", midMarketRate);
        map.put("currencyPair", currencyPair);
        map.put("depositRequired", depositRequired);
        map.put("depositAmount", depositAmount);
        map.put("depositCurrency", depositCurrency);
        map.put("createdAt", createdAt);
        map.put("expiresAt", expiresAt);
        map.put("settlementCutOffTime", settlementCutOffTime);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
