package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conversion implements Entity {

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
    private String uniqueRequestId;
    private List<String> conversionIds = new ArrayList<>();
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Date conversionDateFrom;
    private Date conversionDateTo;
    private BigDecimal partnerBuyAmountFrom;
    private BigDecimal partnerBuyAmountTo;
    private BigDecimal partnerSellAmountFrom;
    private BigDecimal partnerSellAmountTo;
    private BigDecimal buyAmountFrom;
    private BigDecimal buyAmountTo;
    private BigDecimal sellAmountFrom;
    private BigDecimal sellAmountTo;
    private String scope;
    private Date settlementDateFrom;
    private Date settlementDateTo;
    private String bulkUploadId;
    private BigDecimal unallocatedFunds;
    private String reason;
    private BigDecimal amount;
    private Boolean termAgreement;

    protected Conversion() { }

    private Conversion(
            String buyCurrency,
            String sellCurrency,
            String fixedSide,
            BigDecimal amount,
            Boolean termAgreement
    ) {
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.fixedSide = fixedSide;
        this.amount = amount;
        this.termAgreement = termAgreement;
    }

    /**
     * @deprecated as of 1.2.3; use {@link #Conversion(String, String, String, BigDecimal, Boolean)} instead
     */
    @Deprecated
    private Conversion (
            String buyCurrency,
            String sellCurrency,
            String fixedSide,
            @Nullable Date conversionDate,
            @Nullable BigDecimal clientRate,
            @Nullable String currencyPair,
            @Nullable BigDecimal clientBuyAmount,
            @Nullable BigDecimal clientSellAmount,
            @Nullable String uniqueRequestId
    ) {
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.fixedSide = fixedSide;
        this.conversionDate = conversionDate;
        this.clientRate = clientRate;
        this.currencyPair = currencyPair;
        this.clientBuyAmount = clientBuyAmount;
        this.clientSellAmount = clientSellAmount;
        this.uniqueRequestId = uniqueRequestId;
    }

    /**
     * @deprecated as of 1.2.3; use {@link #Conversion(String, String, String, BigDecimal, Boolean)} instead
     */
    @Deprecated
    private Conversion(
            @Nullable String shortReference,
            @Nullable String status,
            @Nullable String partnerStatus,
            @Nullable String buyCurrency,
            @Nullable String sellCurrency,
            @Nullable String currencyPair,
            @Nullable String uniqueRequestId
    ) {
        this.shortReference = shortReference;
        this.status = status;
        this.partnerStatus = partnerStatus;
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.currencyPair = currencyPair;
        this.uniqueRequestId = uniqueRequestId;
    }

    public static Conversion create() {
        return new Conversion();
    }

    /** Creates a Conversion with only the required properties for creation. */
    public static Conversion create(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, Boolean termAgreement) {
        return new Conversion(buyCurrency, sellCurrency, fixedSide, amount, termAgreement);
    }

    /**
     * @deprecated as of 1.2.3; use {@link #create(String, String, String, BigDecimal, Boolean)} instead
     */
    public static Conversion create(String buyCurrency, String sellCurrency, String fixedSide) {
        return new Conversion(buyCurrency, sellCurrency, fixedSide, null, null, null, null, null, null);
    }

    /**
     * @deprecated as of 1.2.3; use {@link #create(String, String, String, BigDecimal, Boolean)} instead or
     * {@link #create()} calling the appropiate setter methods
     */
    public static Conversion create(
            String buyCurrency,
            String sellCurrency,
            String fixedSide,
            @Nullable Date conversionDate,
            @Nullable BigDecimal clientRate,
            @Nullable String currencyPair,
            @Nullable BigDecimal clientBuyAmount,
            @Nullable BigDecimal clientSellAmount,
            @Nullable String uniqueRequestId
    ) {
        return new Conversion(
                buyCurrency,
                sellCurrency,
                fixedSide,
                conversionDate,
                clientRate,
                currencyPair,
                clientBuyAmount,
                clientSellAmount,
                uniqueRequestId
        );
    }

    /**
     * @deprecated as of 1.2.3; use {@link #create()} calling the appropiate setter methods and pass the resulting
     * object to CurrencyCloudClient.findConversions(Conversion, Pagination) instead
     */
    public static Conversion createExample(
            @Nullable String shortReference,
            @Nullable String status,
            @Nullable String partnerStatus,
            @Nullable String buyCurrency,
            @Nullable String sellCurrency,
            @Nullable String currencyPair,
            @Nullable String uniqueRequestId
    ) {
        return new Conversion(shortReference, status, partnerStatus, buyCurrency, sellCurrency, currencyPair, uniqueRequestId);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public void setCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
    }

    public String getShortReference() {
        return shortReference;
    }

    public void setShortReference(String shortReference) {
        this.shortReference = shortReference;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Date getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
        this.conversionDate = conversionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
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

    public BigDecimal getMidMarketRate() {
        return midMarketRate;
    }

    public void setMidMarketRate(BigDecimal midMarketRate) {
        this.midMarketRate = midMarketRate;
    }

    public BigDecimal getCoreRate() {
        return coreRate;
    }

    public void setCoreRate(BigDecimal coreRate) {
        this.coreRate = coreRate;
    }

    public BigDecimal getPartnerRate() {
        return partnerRate;
    }

    public void setPartnerRate(BigDecimal partnerRate) {
        this.partnerRate = partnerRate;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
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

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Date getDepositRequiredAt() {
        return depositRequiredAt;
    }

    public void setDepositRequiredAt(Date depositRequiredAt) {
        this.depositRequiredAt = depositRequiredAt;
    }

    public List<String> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(List<String> paymentIds) {
        this.paymentIds = paymentIds;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public List<String> getConversionIds() {
        return conversionIds;
    }

    public void setConversionIds(List<String> conversionIds) {
        this.conversionIds = conversionIds;
    }

    public Date getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(Date createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public Date getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(Date createdAtTo) {
        this.createdAtTo = createdAtTo;
    }

    public Date getUpdatedAtFrom() {
        return updatedAtFrom;
    }

    public void setUpdatedAtFrom(Date updatedAtFrom) {
        this.updatedAtFrom = updatedAtFrom;
    }

    public Date getUpdatedAtTo() {
        return updatedAtTo;
    }

    public void setUpdatedAtTo(Date updatedAtTo) {
        this.updatedAtTo = updatedAtTo;
    }

    public Date getConversionDateFrom() {
        return conversionDateFrom;
    }

    public void setConversionDateFrom(Date conversionDateFrom) {
        this.conversionDateFrom = conversionDateFrom;
    }

    public Date getConversionDateTo() {
        return conversionDateTo;
    }

    public void setConversionDateTo(Date conversionDateTo) {
        this.conversionDateTo = conversionDateTo;
    }

    public BigDecimal getPartnerBuyAmountFrom() {
        return partnerBuyAmountFrom;
    }

    public void setPartnerBuyAmountFrom(BigDecimal partnerBuyAmountFrom) {
        this.partnerBuyAmountFrom = partnerBuyAmountFrom;
    }

    public BigDecimal getPartnerBuyAmountTo() {
        return partnerBuyAmountTo;
    }

    public void setPartnerBuyAmountTo(BigDecimal partnerBuyAmountTo) {
        this.partnerBuyAmountTo = partnerBuyAmountTo;
    }

    public BigDecimal getPartnerSellAmountFrom() {
        return partnerSellAmountFrom;
    }

    public void setPartnerSellAmountFrom(BigDecimal partnerSellAmountFrom) {
        this.partnerSellAmountFrom = partnerSellAmountFrom;
    }

    public BigDecimal getPartnerSellAmountTo() {
        return partnerSellAmountTo;
    }

    public void setPartnerSellAmountTo(BigDecimal partnerSellAmountTo) {
        this.partnerSellAmountTo = partnerSellAmountTo;
    }

    public BigDecimal getBuyAmountFrom() {
        return buyAmountFrom;
    }

    public void setBuyAmountFrom(BigDecimal buyAmountFrom) {
        this.buyAmountFrom = buyAmountFrom;
    }

    public BigDecimal getBuyAmountTo() {
        return buyAmountTo;
    }

    public void setBuyAmountTo(BigDecimal buyAmountTo) {
        this.buyAmountTo = buyAmountTo;
    }

    public BigDecimal getSellAmountFrom() {
        return sellAmountFrom;
    }

    public void setSellAmountFrom(BigDecimal sellAmountFrom) {
        this.sellAmountFrom = sellAmountFrom;
    }

    public BigDecimal getSellAmountTo() {
        return sellAmountTo;
    }

    public void setSellAmountTo(BigDecimal sellAmountTo) {
        this.sellAmountTo = sellAmountTo;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getSettlementDateFrom() {
        return settlementDateFrom;
    }

    public void setSettlementDateFrom(Date settlementDateFrom) {
        this.settlementDateFrom = settlementDateFrom;
    }

    public Date getSettlementDateTo() {
        return settlementDateTo;
    }

    public void setSettlementDateTo(Date settlementDateTo) {
        this.settlementDateTo = settlementDateTo;
    }

    public String getBulkUploadId() {
        return bulkUploadId;
    }

    public void setBulkUploadId(String bulkUploadId) {
        this.bulkUploadId = bulkUploadId;
    }

    public BigDecimal getUnallocatedFunds() {
        return unallocatedFunds;
    }

    public void setUnallocatedFunds(BigDecimal unallocatedFunds) {
        this.unallocatedFunds = unallocatedFunds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getTermAgreement() {
        return termAgreement;
    }

    public void setTermAgreement(Boolean termAgreement) {
        this.termAgreement = termAgreement;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("accountId", accountId)
                .appendField("creatorContactId", creatorContactId)
                .appendField("shortReference", shortReference)
                .appendField("settlementDate", settlementDate)
                .appendField("conversionDate", conversionDate)
                .appendField("status", status)
                .appendField("partnerStatus", partnerStatus)
                .appendField("currencyPair", currencyPair)
                .appendField("buyCurrency", buyCurrency)
                .appendField("sellCurrency", sellCurrency)
                .appendField("fixedSide", fixedSide)
                .appendField("partnerBuyAmount", partnerBuyAmount)
                .appendField("partnerSellAmount", partnerSellAmount)
                .appendField("clientBuyAmount", clientBuyAmount)
                .appendField("clientSellAmount", clientSellAmount)
                .appendField("midMarketRate", midMarketRate)
                .appendField("coreRate", coreRate)
                .appendField("partnerRate", partnerRate)
                .appendField("clientRate", clientRate)
                .appendField("depositRequired", depositRequired)
                .appendField("depositAmount", depositAmount)
                .appendField("depositCurrency", depositCurrency)
                .appendField("depositStatus", depositStatus)
                .appendField("depositRequiredAt", depositRequiredAt)
                .appendField("unallocatedFunds", unallocatedFunds)
                .appendField("paymentIds", paymentIds)
                .appendField("uniqueRequestId", uniqueRequestId)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
        }
}
