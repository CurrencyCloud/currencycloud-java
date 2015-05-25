package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

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

    public String getId() {
        return id;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public String getRelatedEntityShortReference() {
        return relatedEntityShortReference;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public Date getSettlesAt() {
        return settlesAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format("Transaction{id='%s', balanceId='%s', accountId='%s', currency='%s', amount=%s, balanceAmount=%s, type='%s', action='%s', relatedEntityType='%s', relatedEntityId='%s', relatedEntityShortReference='%s', status='%s', reason='%s', settlesAt=%s, createdAt=%s, updatedAt=%s}",
                id, balanceId, accountId, currency, amount, balanceAmount, type, action, relatedEntityType, relatedEntityId, relatedEntityShortReference, status, reason, settlesAt, createdAt, updatedAt);
    }
}
