package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Transaction implements Entity {

    private String id;
    private String balanceId;
    private String accountId;
    private String currency;
    private BigDecimal amount;
    private BigDecimal balanceAmount;
    private String type;
    private String action;
    private String relatedEntityType;
    private String relatedEntityId;
    private String relatedEntityShortReference;
    private String status;
    private String reason;
    private Date settlesAt;
    private Date createdAt;
    private Date updatedAt;

    protected Transaction() { }

    private Transaction(
            String currency,
            BigDecimal amount,
            String action,
            String relatedEntityType,
            String relatedEntityId,
            String relatedEntityShortReference,
            String status,
            String type,
            String reason
    ) {
        this.currency = currency;
        this.amount = amount;
        this.action = action;
        this.relatedEntityType = relatedEntityType;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityShortReference = relatedEntityShortReference;
        this.status = status;
        this.type = type;
        this.reason = reason;
    }

    public static Transaction create() {
        return new Transaction();
    }

    /**
     * Creates a new {@link Transaction} that can be used as a parameter for the
     * {@link com.currencycloud.client.CurrencyCloudClient#findTransactions} method.
     */
    public static Transaction createExample(
            String currency,
            BigDecimal amount,
            String action,
            String relatedEntityType,
            String relatedEntityId,
            String relatedEntityShortReference,
            String status,
            String type,
            String reason
    ) {
        return new Transaction(
                currency,
                amount,
                action,
                relatedEntityType,
                relatedEntityId,
                relatedEntityShortReference,
                status,
                type,
                reason
        );
    }

}
