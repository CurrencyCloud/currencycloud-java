package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conversion {

    private String id;
    private String accountId;
    private String creatorContactId;
    private String shortReference;
    private Date settlementDate;
    private Date conversionDate;
    private String status;
    private String partnerStatus;
    private String currencyPair;
    private String buyCurrency;
    private String sellCurrency;
    private String fixedSide;
    private BigDecimal partnerBuyAmount;
    private BigDecimal partnerSellAmount;
    private BigDecimal clientBuyAmount;
    private BigDecimal clientSellAmount;
    private BigDecimal midMarketRate;
    private BigDecimal coreRate;
    private BigDecimal partnerRate;
    private BigDecimal clientRate;
    private Boolean depositRequired;
    private BigDecimal depositAmount;
    private String depositCurrency;
    private String depositStatus;
    private Date depositRequiredAt;
    private List<String> paymentIds = new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Conversion withId(String id) {
        this.id = id;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Conversion withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public void setCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
    }

    public Conversion withCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
        return this;
    }

    public String getShortReference() {
        return shortReference;
    }

    public void setShortReference(String shortReference) {
        this.shortReference = shortReference;
    }

    public Conversion withShortReference(String shortReference) {
        this.shortReference = shortReference;
        return this;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Conversion withSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
        return this;
    }

    public Date getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }

    public Conversion withConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Conversion withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Conversion withPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
        return this;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Conversion withCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public Conversion withBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
        return this;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public Conversion withSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
        return this;
    }

    public String getFixedSide() {
        return fixedSide;
    }

    public void setFixedSide(String fixedSide) {
        this.fixedSide = fixedSide;
    }

    public Conversion withFixedSide(String fixedSide) {
        this.fixedSide = fixedSide;
        return this;
    }

    public BigDecimal getPartnerBuyAmount() {
        return partnerBuyAmount;
    }

    public void setPartnerBuyAmount(BigDecimal partnerBuyAmount) {
        this.partnerBuyAmount = partnerBuyAmount;
    }

    public Conversion withPartnerBuyAmount(BigDecimal partnerBuyAmount) {
        this.partnerBuyAmount = partnerBuyAmount;
        return this;
    }

    public BigDecimal getPartnerSellAmount() {
        return partnerSellAmount;
    }

    public void setPartnerSellAmount(BigDecimal partnerSellAmount) {
        this.partnerSellAmount = partnerSellAmount;
    }

    public Conversion withPartnerSellAmount(BigDecimal partnerSellAmount) {
        this.partnerSellAmount = partnerSellAmount;
        return this;
    }

    public BigDecimal getClientBuyAmount() {
        return clientBuyAmount;
    }

    public void setClientBuyAmount(BigDecimal clientBuyAmount) {
        this.clientBuyAmount = clientBuyAmount;
    }

    public Conversion withClientBuyAmount(BigDecimal clientBuyAmount) {
        this.clientBuyAmount = clientBuyAmount;
        return this;
    }

    public BigDecimal getClientSellAmount() {
        return clientSellAmount;
    }

    public void setClientSellAmount(BigDecimal clientSellAmount) {
        this.clientSellAmount = clientSellAmount;
    }

    public Conversion withClientSellAmount(BigDecimal clientSellAmount) {
        this.clientSellAmount = clientSellAmount;
        return this;
    }

    public BigDecimal getMidMarketRate() {
        return midMarketRate;
    }

    public void setMidMarketRate(BigDecimal midMarketRate) {
        this.midMarketRate = midMarketRate;
    }

    public Conversion withMidMarketRate(BigDecimal midMarketRate) {
        this.midMarketRate = midMarketRate;
        return this;
    }

    public BigDecimal getCoreRate() {
        return coreRate;
    }

    public void setCoreRate(BigDecimal coreRate) {
        this.coreRate = coreRate;
    }

    public Conversion withCoreRate(BigDecimal coreRate) {
        this.coreRate = coreRate;
        return this;
    }

    public BigDecimal getPartnerRate() {
        return partnerRate;
    }

    public void setPartnerRate(BigDecimal partnerRate) {
        this.partnerRate = partnerRate;
    }

    public Conversion withPartnerRate(BigDecimal partnerRate) {
        this.partnerRate = partnerRate;
        return this;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    public Conversion withClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
        return this;
    }

    public Boolean getDepositRequired() {
        return depositRequired;
    }

    public void setDepositRequired(Boolean depositRequired) {
        this.depositRequired = depositRequired;
    }

    public Conversion withDepositRequired(Boolean depositRequired) {
        this.depositRequired = depositRequired;
        return this;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Conversion withDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
        return this;
    }

    public String getDepositCurrency() {
        return depositCurrency;
    }

    public void setDepositCurrency(String depositCurrency) {
        this.depositCurrency = depositCurrency;
    }

    public Conversion withDepositCurrency(String depositCurrency) {
        this.depositCurrency = depositCurrency;
        return this;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Conversion withDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
        return this;
    }

    public Date getDepositRequiredAt() {
        return depositRequiredAt;
    }

    public void setDepositRequiredAt(Date depositRequiredAt) {
        this.depositRequiredAt = depositRequiredAt;
    }

    public Conversion withDepositRequiredAt(Date depositRequiredAt) {
        this.depositRequiredAt = depositRequiredAt;
        return this;
    }

    public List<String> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(List<String> paymentIds) {
        this.paymentIds = paymentIds;
    }

    public Conversion withPaymentIds(List<String> paymentIds) {
        this.paymentIds = paymentIds;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Conversion withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Conversion withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Conversion{id='%s', accountId='%s', creatorContactId='%s', shortReference='%s', settlementDate=%s, conversionDate=%s, status='%s', partnerStatus='%s', currencyPair='%s', buyCurrency='%s', sellCurrency='%s', fixedSide='%s', partnerBuyAmount=%s, partnerSellAmount=%s, clientBuyAmount=%s, clientSellAmount=%s, midMarketRate=%s, coreRate=%s, partnerRate=%s, clientRate=%s, depositRequired=%s, depositAmount=%s, depositCurrency='%s', depositStatus='%s', depositRequiredAt=%s, paymentIds=%s, createdAt=%s, updatedAt=%s}",
                id, accountId, creatorContactId, shortReference, settlementDate, conversionDate, status, partnerStatus, currencyPair, buyCurrency, sellCurrency, fixedSide, partnerBuyAmount, partnerSellAmount, clientBuyAmount, clientSellAmount, midMarketRate, coreRate, partnerRate, clientRate, depositRequired, depositAmount, depositCurrency, depositStatus, depositRequiredAt, paymentIds, createdAt, updatedAt);
    }
}