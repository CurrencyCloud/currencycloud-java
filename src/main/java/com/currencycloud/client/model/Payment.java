
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
}