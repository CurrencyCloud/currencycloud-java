
package com.currencycloud.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {

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

    private Payment() { }

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
                    String status
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
    }

    public static Payment createEmpty() {
        return new Payment();
    }

    public static Payment createForCreate(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String reference,
            String conversionId,
            Date paymentDate,
            String paymentType
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, reference, conversionId, paymentDate, paymentType, null, null);
    }

    public static Payment createForUpdate(
            String id,
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String reference,
            String conversionId,
            Date paymentDate,
            String paymentType
    ) {
        return new Payment(id, currency, beneficiaryId, amount, reason, reference, conversionId, paymentDate, paymentType, null, null);
    }

    public static Payment createForFind(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String conversionId,
            String shortReference,
            String status
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, null, conversionId, null, null, shortReference, status);
    }

    public String getId() {
        return id;
    }

    public String getShortReference() {
        return shortReference;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public String getConversionId() {
        return conversionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getReference() {
        return reference;
    }

    public String getReason() {
        return reason;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Date getTransferredAt() {
        return transferredAt;
    }

    public Integer getAuthorisationStepsRequired() {
        return authorisationStepsRequired;
    }

    public String getCreatorContactId() {
        return creatorContactId;
    }

    public String getLastUpdaterContactId() {
        return lastUpdaterContactId;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getPayerId() {
        return payerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format("Payment{id='%s', shortReference='%s', beneficiaryId='%s', conversionId='%s', amount=%s, currency='%s', status='%s', paymentType='%s', reference='%s', reason='%s', paymentDate=%s, transferredAt=%s, authorisationStepsRequired=%d, creatorContactId='%s', lastUpdaterContactId='%s', failureReason='%s', payerId='%s', createdAt=%s, updatedAt=%s}",
                id, shortReference, beneficiaryId, conversionId, amount, currency, status, paymentType, reference, reason, paymentDate, transferredAt, authorisationStepsRequired, creatorContactId, lastUpdaterContactId, failureReason, payerId, createdAt, updatedAt);
    }
}