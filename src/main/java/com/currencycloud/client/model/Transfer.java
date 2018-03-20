package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transfer implements Entity {

    private String id;
    private String shortReference;
    private String sourceAccountId;
    private String destinationAccountId;
    private String currency;
    private BigDecimal amount;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;
    private String creatorAccountId;
    private String creatorContactId;
    private String reason;

    protected Transfer() {};

    private Transfer(String sourceAccountId,
                     String destinationAccountId,
                     String currency,
                     BigDecimal amount,
                     @Nullable String reason
    ) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.currency = currency;
        this.amount = amount;
        this.reason = reason;
    }

    /**
     * Creates a transfer as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createTransfer(Transfer)} method,
     * using only required parameters.
     */

    public static Transfer create(String sourceAccountId,
                                  String destinationAccountId,
                                  String currency,
                                  BigDecimal amount) {
        return new Transfer(sourceAccountId, destinationAccountId, currency, amount, null);

    }

    /**
     * Creates a transfer as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createTransfer(Transfer)} method,
     * using all parameters.
     */

    public static Transfer create(String sourceAccountId,
                                  String destinationAccountId,
                                  String currency,
                                  BigDecimal amount,
                                  @Nullable String reason) {
        return new Transfer(sourceAccountId, destinationAccountId, currency, amount, reason);

    }

    public static Transfer create() {
        return new Transfer();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getShortReference() {
        return shortReference;
    }

    public void setShortReference(String shortReference) {
        this.shortReference = shortReference;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public String getCreatorAccountId() {
        return creatorAccountId;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Transfer{id='%s', shortReference='%s', sourceAccountId='%s', destinationAccountId='%s', currency='%s', amount=%s, status='%s', createdAt=%s, updatedAt=%s, completedAt=%s, creatorAccountId='%s', creatorContactId='%s', reason='%s'}",
                id, shortReference, sourceAccountId, destinationAccountId, currency, amount, status, createdAt, updatedAt, completedAt, creatorAccountId, creatorContactId, reason);
    }
}
