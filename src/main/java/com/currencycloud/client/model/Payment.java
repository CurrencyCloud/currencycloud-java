package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
    private String paymentGroupId;
    private BigDecimal failureReturnedAmount;
    private String ultimateBeneficiaryName;
    private String payerDetailsSource;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private Date paymentDateFrom;
    private Date paymentDateTo;
    private Date transferredAtFrom;
    private Date transferredAtTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Boolean withDeleted;
    private String scope;
    private String bulkUploadId;
    private String purposeCode;

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

    public void setId(String id) {
        this.id = id;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public String getPaymentGroupId() {
        return paymentGroupId;
    }

    public void setPaymentGroupId(String paymentGroupId) {
        this.paymentGroupId = paymentGroupId;
    }

    public String getUltimateBeneficiaryName() {
        return ultimateBeneficiaryName;
    }

    public void setUltimateBeneficiaryName(String ultimateBeneficiaryName) {
        this.ultimateBeneficiaryName = ultimateBeneficiaryName;
    }

    public String getPayerDetailsSource() {
        return payerDetailsSource;
    }

    public void setPayerDetailsSource(String payerDetailsSource) {
        this.payerDetailsSource = payerDetailsSource;
    }

    public BigDecimal getFailureReturnedAmount() {
        return failureReturnedAmount;
    }

    public void setFailureReturnedAmount(BigDecimal failureReturnedAmount) {
        this.failureReturnedAmount = failureReturnedAmount;
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

    public Date getPaymentDateFrom() {
        return paymentDateFrom;
    }

    public void setPaymentDateFrom(Date paymentDateFrom) {
        this.paymentDateFrom = paymentDateFrom;
    }

    public Date getPaymentDateTo() {
        return paymentDateTo;
    }

    public void setPaymentDateTo(Date paymentDateTo) {
        this.paymentDateTo = paymentDateTo;
    }

    public Date getTransferredAtFrom() {
        return transferredAtFrom;
    }

    public void setTransferredAtFrom(Date transferredAtFrom) {
        this.transferredAtFrom = transferredAtFrom;
    }

    public Date getTransferredAtTo() {
        return transferredAtTo;
    }

    public void setTransferredAtTo(Date transferredAtTo) {
        this.transferredAtTo = transferredAtTo;
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

    public Boolean getWithDeleted() {
        return withDeleted;
    }

    public void setWithDeleted(Boolean withDeleted) {
        this.withDeleted = withDeleted;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getBulkUploadId() {
        return bulkUploadId;
    }

    public void setBulkUploadId(String bulkUploadId) {
        this.bulkUploadId = bulkUploadId;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("shortReference", shortReference);
        map.put("beneficiaryId", beneficiaryId);
        map.put("conversionId", conversionId);
        map.put("amount", amount);
        map.put("currency", currency);
        map.put("status", status);
        map.put("paymentType", paymentType);
        map.put("reference", reference);
        map.put("reason", reason);
        map.put("paymentDate", paymentDate);
        map.put("transferredAt", transferredAt);
        map.put("authorisationStepsRequired", authorisationStepsRequired);
        map.put("creatorContactId", creatorContactId);
        map.put("lastUpdaterContactId", lastUpdaterContactId);
        map.put("failureReason", failureReason);
        map.put("payerId", payerId);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("uniqueRequestId", uniqueRequestId);
        map.put("failureReturnedAmount", failureReturnedAmount);
        map.put("payerDetailsSource", payerDetailsSource);
        map.put("paymentGroupId", paymentGroupId);
        map.put("ultimateBeneficiaryName", ultimateBeneficiaryName);
        map.put("purposeCode", purposeCode);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
