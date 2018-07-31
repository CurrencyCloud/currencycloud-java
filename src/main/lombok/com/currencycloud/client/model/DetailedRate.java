package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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