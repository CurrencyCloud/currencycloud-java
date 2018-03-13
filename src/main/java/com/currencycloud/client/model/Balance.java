package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Balance implements Entity {

    protected Balance() { }
    private String id;
    private String accountId;
    private String currency;
    private BigDecimal amount;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format("Balance{id='%s', accountId='%s', currency='%s', amount=%s, createdAt=%s, updatedAt=%s}",
                id, accountId, currency, amount, createdAt, updatedAt);
    }
}
