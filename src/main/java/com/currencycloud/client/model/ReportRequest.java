package com.currencycloud.client.model;

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
public class ReportRequest implements Entity {

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
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date expirationDateFrom;
    private Date expirationDateTo;

    protected ReportRequest() {}

    public static ReportRequest create() { return new ReportRequest(); }

    private ReportRequest(String id) {
        this.id = id;
    }

    public static ReportRequest create(String id) {
        return new ReportRequest(id);
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

    public Date getExpirationDateFrom() {
        return expirationDateFrom;
    }

    public void setExpirationDateFrom(Date expirationDateFrom) {
        this.expirationDateFrom = expirationDateFrom;
    }

    public Date getExpirationDateTo() {
        return expirationDateTo;
    }

    public void setExpirationDateTo(Date expirationDateTo) {
        this.expirationDateTo = expirationDateTo;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
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
        private String buyCurrency;
        private String sellCurrency;
        private BigDecimal clientBuyAmountFrom;
        private BigDecimal clientBuyAmountTo;
        private BigDecimal clientSellAmountFrom;
        private BigDecimal clientSellAmountTo;
        private BigDecimal partnerBuyAmountFrom;
        private BigDecimal partnerBuyAmountTo;
        private BigDecimal partnerSellAmountFrom;
        private BigDecimal partnerSellAmountTo;
        private String clientStatus;
        private String partnerStatus;
        private Date conversionDateFrom;
        private Date conversionDateTo;
        private Date settlementDateFrom;
        private Date settlementDateTo;

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

        public String getBuyCurrency() {
            return buyCurrency;
        }

        public String getSellCurrency() {
            return sellCurrency;
        }

        public BigDecimal getClientBuyAmountFrom() {
            return clientBuyAmountFrom;
        }

        public BigDecimal getClientBuyAmountTo() {
            return clientBuyAmountTo;
        }

        public BigDecimal getClientSellAmountFrom() {
            return clientSellAmountFrom;
        }

        public BigDecimal getClientSellAmountTo() {
            return clientSellAmountTo;
        }

        public BigDecimal getPartnerBuyAmountFrom() {
            return partnerBuyAmountFrom;
        }

        public BigDecimal getPartnerBuyAmountTo() {
            return partnerBuyAmountTo;
        }

        public BigDecimal getPartnerSellAmountFrom() {
            return partnerSellAmountFrom;
        }

        public BigDecimal getPartnerSellAmountTo() {
            return partnerSellAmountTo;
        }

        public String getClientStatus() {
            return clientStatus;
        }

        public String getPartnerStatus() {
            return partnerStatus;
        }

        public Date getConversionDateFrom() {
            return conversionDateFrom;
        }

        public Date getConversionDateTo() {
            return conversionDateTo;
        }

        public Date getSettlementDateFrom() {
            return settlementDateFrom;
        }

        public Date getSettlementDateTo() {
            return settlementDateTo;
        }

        @Override
        public String toString() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
            Map<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("buyCurrency", buyCurrency);
            map.put("sellCurrency", sellCurrency);
            map.put("clientBuyAmountFrom", clientBuyAmountFrom);
            map.put("clientBuyAmountTo", clientBuyAmountTo);
            map.put("clientSellAmountFrom", clientSellAmountFrom);
            map.put("clientSellAmountTo", clientSellAmountTo);
            map.put("partnerBuyAmountFrom", partnerBuyAmountFrom);
            map.put("partnerBuyAmountTo", partnerBuyAmountTo);
            map.put("partnerSellAmountFrom", partnerSellAmountFrom);
            map.put("partnerSellAmountTo", partnerSellAmountTo);
            map.put("clientStatus", clientStatus);
            map.put("partnerStatus", partnerStatus);
            map.put("conversionDateFrom", conversionDateFrom);
            map.put("conversionDateTo", conversionDateTo);
            map.put("settlementDateFrom", settlementDateFrom);
            map.put("settlementDateTo", settlementDateTo);
            map.put("createdAtFrom", createdAtFrom);
            map.put("createdAtTo", createdAtTo);
            map.put("updatedAtFrom", updatedAtFrom);
            map.put("updatedAtTo", updatedAtTo);
            map.put("uniqueRequestId", uniqueRequestId);
            map.put("scope", scope);
            map.put("currency", currency);
            map.put("amountFrom", amountFrom);
            map.put("amountTo", amountTo);
            map.put("status", status);
            map.put("paymentDateFrom", paymentDateFrom);
            map.put("paymentDateTo", paymentDateTo);
            map.put("transferredAtFrom", transferredAtFrom);
            map.put("transferredAtTo", transferredAtTo);
            map.put("beneficiaryId", beneficiaryId);
            map.put("conversionId", conversionId);
            map.put("withDeleted", withDeleted);
            map.put("paymentGroupId", paymentGroupId);
            map.values().removeIf(Objects::isNull);

            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return String.format("{\"error\": \"%s\"}", e.getMessage());
            }
        }
    }
}
