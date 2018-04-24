package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedRate {

    private Date settlementCutOffTime;
    private List<String> currencyPair;
    private String clientBuyCurrency;
    private String clientSellCurrency;
    private BigDecimal clientBuyAmount;
    private BigDecimal clientSellAmount;
    private String fixedSide;
    private BigDecimal midMarketRate;
    private BigDecimal coreRate;
    private BigDecimal partnerRate;
    private BigDecimal clientRate;
    private Boolean depositRequired;
    private BigDecimal depositAmount;
    private String depositCurrency;
    private Date conversionDate;

    protected DetailedRate() { }

    public static DetailedRate create() {
        return new DetailedRate();
    }

    public Date getSettlementCutOffTime() {
        return settlementCutOffTime;
    }

    public List<String> getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(List<String> currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getClientBuyCurrency() {
        return clientBuyCurrency;
    }

    public void setClientBuyCurrency(String clientBuyCurrency) {
        this.clientBuyCurrency = clientBuyCurrency;
    }

    public String getClientSellCurrency() {
        return clientSellCurrency;
    }

    public void setClientSellCurrency(String clientSellCurrency) {
        this.clientSellCurrency = clientSellCurrency;
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

    public String getFixedSide() {
        return fixedSide;
    }

    public void setFixedSide(String fixedSide) {
        this.fixedSide = fixedSide;
    }

    public BigDecimal getMidMarketRate() {
        return midMarketRate;
    }

    public BigDecimal getCoreRate() {
        return coreRate;
    }

    public BigDecimal getPartnerRate() {
        return partnerRate;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public Boolean getDepositRequired() {
        return depositRequired;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public String getDepositCurrency() {
        return depositCurrency;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("settlementCutOffTime", settlementCutOffTime)
                .appendField("currencyPair", currencyPair)
                .appendField("clientBuyCurrency", clientBuyCurrency)
                .appendField("clientSellCurrency", clientSellCurrency)
                .appendField("clientBuyAmount", clientBuyAmount)
                .appendField("clientSellAmount", clientSellAmount)
                .appendField("fixedSide", fixedSide)
                .appendField("midMarketRate", midMarketRate)
                .appendField("coreRate", coreRate)
                .appendField("partnerRate", partnerRate)
                .appendField("clientRate", clientRate)
                .appendField("depositRequired", depositRequired)
                .appendField("depositAmount", depositAmount)
                .appendField("depositCurrency", depositCurrency)
                .toString();
    }
}
