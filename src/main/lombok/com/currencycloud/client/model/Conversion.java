package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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
}
