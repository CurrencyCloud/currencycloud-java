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

    protected Conversion() { }

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
    public static Conversion create(String buyCurrency, String sellCurrency, String fixedSide) {
        return new Conversion(buyCurrency, sellCurrency, fixedSide, null, null, null, null, null, null);
    }

    /** Creates a Conversion with the required and the optional properties for creation. */
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

    /** Creates the Conversion with the properties that can be passed to the
     * {@link com.currencycloud.client.CurrencyCloudClient#findConversions} method. */
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
