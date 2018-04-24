package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getRelatedEntityShortReference() {
        return relatedEntityShortReference;
    }

    public void setRelatedEntityShortReference(String relatedEntityShortReference) {
        this.relatedEntityShortReference = relatedEntityShortReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getSettlesAt() {
        return settlesAt;
    }

    public void setSettlesAt(Date settlesAt) {
        this.settlesAt = settlesAt;
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

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public Date getSettlesAtFrom() {
        return settlesAtFrom;
    }

    public void setSettlesAtFrom(Date settlesAtFrom) {
        this.settlesAtFrom = settlesAtFrom;
    }

    public Date getSettlesAtTo() {
        return settlesAtTo;
    }

    public void setSettlesAtTo(Date settlesAtTo) {
        this.settlesAtTo = settlesAtTo;
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

    public Date getCompletedAtFrom() {
        return completedAtFrom;
    }

    public void setCompletedAtFrom(Date completedAtFrom) {
        this.completedAtFrom = completedAtFrom;
    }

    public Date getCompletedAtTo() {
        return completedAtTo;
    }

    public void setCompletedAtTo(Date completedAtTo) {
        this.completedAtTo = completedAtTo;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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
