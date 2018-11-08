package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentReport implements Entity {

    private String id;
    private String shortReference;
    private String description;
    private SearchParams searchParams;
    private String reportType;
    private String status;
    private String failureReason;
    private Date expirationDate;
    private String reportUrl;
    private String accountId;
    private String contactId;
    private Date createdAt;
    private Date updatedAt;
    private String currency;
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
    private String beneficiaryId;
    private String conversionId;
    private Boolean withDeleted;
    private String paymentGroupId;
    private String uniqueRequestId;
    private String scope;

    protected PaymentReport() {}

    public static PaymentReport create() { return new PaymentReport(); }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SearchParams getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Boolean getWithDeleted() {
        return withDeleted;
    }

    public void setWithDeleted(Boolean withDeleted) {
        this.withDeleted = withDeleted;
    }

    public String getPaymentGroupId() {
        return paymentGroupId;
    }

    public void setPaymentGroupId(String paymentGroupId) {
        this.paymentGroupId = paymentGroupId;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("shortReference", shortReference);
        map.put("description", description);
        map.put("searchParams", searchParams);
        map.put("reportType", reportType);
        map.put("status", status);
        map.put("failureReason", failureReason);
        map.put("expirationDate", expirationDate);
        map.put("reportUrl", reportUrl);
        map.put("accountId", accountId);
        map.put("contactId", contactId);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    /* ToDo: Not the most efficient way to handle SearchParams. To be improved in a future release */
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchParams {

        public SearchParams() { }

        private String description;
        private String currency;
        private BigDecimal amountFrom;
        private BigDecimal amountTo;
        private String status;
        private Date paymentDateFrom;
        private Date paymentDateTo;
        private Date transferredAtFrom;
        private Date transferredAtTo;
        private Date createdAtFrom;
        private Date createdAtTo;
        private Date updatedAtFrom;
        private Date updatedAtTo;
        private String beneficiaryId;
        private String conversionId;
        private Boolean withDeleted;
        private String paymentGroupId;
        private String uniqueRequestId;
        private String scope;

        public String getDescription() {
            return description;
        }

        public String getCurrency() {
            return currency;
        }

        public BigDecimal getAmountFrom() {
            return amountFrom;
        }

        public BigDecimal getAmountTo() {
            return amountTo;
        }

        public String getStatus() {
            return status;
        }

        public Date getPaymentDateFrom() {
            return paymentDateFrom;
        }

        public Date getPaymentDateTo() {
            return paymentDateTo;
        }

        public Date getTransferredAtFrom() {
            return transferredAtFrom;
        }

        public Date getTransferredAtTo() {
            return transferredAtTo;
        }

        public Date getCreatedAtFrom() {
            return createdAtFrom;
        }

        public Date getCreatedAtTo() {
            return createdAtTo;
        }

        public Date getUpdatedAtFrom() {
            return updatedAtFrom;
        }

        public Date getUpdatedAtTo() {
            return updatedAtTo;
        }

        public String getBeneficiaryId() {
            return beneficiaryId;
        }

        public String getConversionId() {
            return conversionId;
        }

        public Boolean getWithDeleted() {
            return withDeleted;
        }

        public String getPaymentGroupId() {
            return paymentGroupId;
        }

        public String getUniqueRequestId() {
            return uniqueRequestId;
        }

        public String getScope() {
            return scope;
        }

        @Override
        public String toString() {
            final ObjectMapper objectMapper = new ObjectMapper()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

            Map<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("currency", currency);
            map.put("amountFrom", amountFrom);
            map.put("amountTo", amountTo);
            map.put("status", status);
            map.put("paymentDateFrom", paymentDateFrom);
            map.put("paymentDateTo", paymentDateTo);
            map.put("transferredAtFrom", transferredAtFrom);
            map.put("transferredAtTo", transferredAtTo);
            map.put("createdAtFrom", createdAtFrom);
            map.put("createdAtTo", createdAtTo);
            map.put("updatedAtFrom", updatedAtFrom);
            map.put("updatedAtTo", updatedAtTo);
            map.put("beneficiaryId", beneficiaryId);
            map.put("conversionId", conversionId);
            map.put("withDeleted", withDeleted);
            map.put("paymentGroupId", paymentGroupId);
            map.put("uniqueRequestId", uniqueRequestId);
            map.put("scope", scope);
            map.values().removeIf(Objects::isNull);

            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return String.format("{\"error\": \"%s\"}", e.getMessage());
            }
        }
    }
}
