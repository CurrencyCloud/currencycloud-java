package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
    private Date completedAt;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private Date settlesAtFrom;
    private Date settlesAtTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Date completedAtFrom;
    private Date completedAtTo;
    private String beneficiaryId;
    private String currencyPair;
    private String scope;

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

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("balanceId", balanceId)
                .appendField("accountId", accountId)
                .appendField("currency", currency)
                .appendField("amount", amount)
                .appendField("balanceAmount", balanceAmount)
                .appendField("type", type)
                .appendField("action", action)
                .appendField("relatedEntityType", relatedEntityType)
                .appendField("relatedEntityId", relatedEntityId)
                .appendField("relatedEntityShortReference", relatedEntityShortReference)
                .appendField("status", status)
                .appendField("reason", reason)
                .appendField("settlesAt", settlesAt)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .appendField("completedAt", completedAt)
                .toString();
    }
}
