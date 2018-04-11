package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailedRate {

    private Date settlementCutOffTime;
    private String currencyPair;
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

    public String getCurrencyPair() {
        return currencyPair;
    }

    public String getClientBuyCurrency() {
        return clientBuyCurrency;
    }

    public String getClientSellCurrency() {
        return clientSellCurrency;
    }

    public BigDecimal getClientBuyAmount() {
        return clientBuyAmount;
    }

    public BigDecimal getClientSellAmount() {
        return clientSellAmount;
    }

    public String getFixedSide() {
        return fixedSide;
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
        return String.format("DetailedRate{settlementCutOffTime=%s, currencyPair='%s', clientBuyCurrency='%s', clientSellCurrency='%s', clientBuyAmount=%s, clientSellAmount=%s, fixedSide='%s', midMarketRate=%s, coreRate=%s, partnerRate=%s, clientRate=%s, depositRequired=%s, depositAmount=%s, depositCurrency='%s'}",
                settlementCutOffTime, currencyPair, clientBuyCurrency, clientSellCurrency, clientBuyAmount, clientSellAmount, fixedSide, midMarketRate, coreRate, partnerRate, clientRate, depositRequired, depositAmount, depositCurrency);
    }
}
