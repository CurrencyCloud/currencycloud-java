package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Date completedAtFrom;
    private Date completedAtTo;

    protected Transfer() { }

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

    public static Transfer create() {
        return new Transfer();
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

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreatorAccountId() {
        return creatorAccountId;
    }

    public void setCreatorAccountId(String creatorAccountId) {
        this.creatorAccountId = creatorAccountId;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public void setCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("shortReference", shortReference);
        map.put("sourceAccountId", sourceAccountId);
        map.put("destinationAccountId", destinationAccountId);
        map.put("currency", currency);
        map.put("amount", amount);
        map.put("status", status);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("completedAt", completedAt);
        map.put("creatorAccountId", creatorAccountId);
        map.put("creatorContactId", creatorContactId);
        map.put("reason", reason);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
