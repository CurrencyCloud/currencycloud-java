package com.currencycloud.client.model;


import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Payment.class)
public class Payment implements Entity {

    private String id;
    private String shortReference;
    private String beneficiaryId;
    private String conversionId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String paymentType;
    private String reference;
    private String reason;
    private Date paymentDate;
    private Date transferredAt;
    private Integer authorisationStepsRequired;
    private String creatorContactId;
    private String lastUpdaterContactId;
    private String failureReason;
    private String payerId;
    private Date createdAt;
    private Date updatedAt;
    private String uniqueRequestId;

    protected Payment() { }

    private Payment(String id,
                    String currency,
                    String beneficiaryId,
                    BigDecimal amount,
                    String reason,
                    String reference,
                    String conversionId,
                    Date paymentDate,
                    String paymentType,
                    String shortReference,
                    String status,
                    @Nullable String uniqueRequestId
    ) {
        this.id = id;
        this.currency = currency;
        this.beneficiaryId = beneficiaryId;
        this.amount = amount;
        this.reason = reason;
        this.reference = reference;
        this.conversionId = conversionId;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.shortReference = shortReference;
        this.status = status;
        this.uniqueRequestId = uniqueRequestId;
    }

    public static Payment create() {
        return new Payment();
    }

    /** Creates a new {@link Payment} that can be used as a parameter for the
     * {@link com.currencycloud.client.CurrencyCloudClient#createPayment(Payment, Payer)} method.
     */
    public static Payment create(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String reference,
            @Nullable Date paymentDate,
            @Nullable String paymentType,
            @Nullable String conversionId,
            @Nullable String uniqueRequestId
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, reference, conversionId, paymentDate, paymentType, null, null, uniqueRequestId);
    }

    /** Creates a new {@link Payment} that can be used a as parameter for the
     * {@link com.currencycloud.client.CurrencyCloudClient#findPayments} method.
     */
    public static Payment createExample(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String conversionId,
            String shortReference,
            String status,
            @Nullable String uniqueRequestId
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, null, conversionId, null, null, shortReference, status, uniqueRequestId);
    }

    public String getId() {
        return id;
    }

    public String getShortReference() {
        return shortReference;
    }

    public void setShortReference(String shortReference) {
        this.shortReference = shortReference;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getTransferredAt() {
        return transferredAt;
    }

    public void setTransferredAt(Date transferredAt) {
        this.transferredAt = transferredAt;
    }

    public Integer getAuthorisationStepsRequired() {
        return authorisationStepsRequired;
    }

    public void setAuthorisationStepsRequired(Integer authorisationStepsRequired) {
        this.authorisationStepsRequired = authorisationStepsRequired;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public void setCreatorContactId(String creatorContactId) {
        this.creatorContactId = creatorContactId;
    }

    public String getLastUpdaterContactId() {
        return lastUpdaterContactId;
    }

    public void setLastUpdaterContactId(String lastUpdaterContactId) {
        this.lastUpdaterContactId = lastUpdaterContactId;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    @Override
    public String toString() {
        return String.format("Payment{id='%s', shortReference='%s', beneficiaryId='%s', conversionId='%s', amount=%s, currency='%s', status='%s', paymentType='%s', reference='%s', reason='%s', paymentDate=%s, transferredAt=%s, authorisationStepsRequired=%d, creatorContactId='%s', lastUpdaterContactId='%s', failureReason='%s', payerId='%s', createdAt=%s, updatedAt=%s, uniqueRequestId=%s}",
                id, shortReference, beneficiaryId, conversionId, amount, currency, status, paymentType, reference, reason, paymentDate, transferredAt, authorisationStepsRequired, creatorContactId, lastUpdaterContactId, failureReason, payerId, createdAt, updatedAt, uniqueRequestId);
    }
}
