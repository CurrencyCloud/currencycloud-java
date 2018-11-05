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
public class ConversionReport implements Entity {

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
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private String uniqueRequestId;
    private String scope;

    protected ConversionReport() {}

    public static ConversionReport create() { return new ConversionReport(); }

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

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public BigDecimal getClientBuyAmountFrom() {
        return clientBuyAmountFrom;
    }

    public void setClientBuyAmountFrom(BigDecimal clientBuyAmountFrom) {
        this.clientBuyAmountFrom = clientBuyAmountFrom;
    }

    public BigDecimal getClientBuyAmountTo() {
        return clientBuyAmountTo;
    }

    public void setClientBuyAmountTo(BigDecimal clientBuyAmountTo) {
        this.clientBuyAmountTo = clientBuyAmountTo;
    }

    public BigDecimal getClientSellAmountFrom() {
        return clientSellAmountFrom;
    }

    public void setClientSellAmountFrom(BigDecimal clientSellAmountFrom) {
        this.clientSellAmountFrom = clientSellAmountFrom;
    }

    public BigDecimal getClientSellAmountTo() {
        return clientSellAmountTo;
    }

    public void setClientSellAmountTo(BigDecimal clientSellAmountTo) {
        this.clientSellAmountTo = clientSellAmountTo;
    }

    public BigDecimal getPartnerBuyAmountFrom() {
        return partnerBuyAmountFrom;
    }

    public void setPartnerBuyAmountFrom(BigDecimal partnerBuyAmountFrom) {
        this.partnerBuyAmountFrom = partnerBuyAmountFrom;
    }

    public BigDecimal getPartnerBuyAmountTo() {
        return partnerBuyAmountTo;
    }

    public void setPartnerBuyAmountTo(BigDecimal partnerBuyAmountTo) {
        this.partnerBuyAmountTo = partnerBuyAmountTo;
    }

    public BigDecimal getPartnerSellAmountFrom() {
        return partnerSellAmountFrom;
    }

    public void setPartnerSellAmountFrom(BigDecimal partnerSellAmountFrom) {
        this.partnerSellAmountFrom = partnerSellAmountFrom;
    }

    public BigDecimal getPartnerSellAmountTo() {
        return partnerSellAmountTo;
    }

    public void setPartnerSellAmountTo(BigDecimal partnerSellAmountTo) {
        this.partnerSellAmountTo = partnerSellAmountTo;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Date getConversionDateFrom() {
        return conversionDateFrom;
    }

    public void setConversionDateFrom(Date conversionDateFrom) {
        this.conversionDateFrom = conversionDateFrom;
    }

    public Date getConversionDateTo() {
        return conversionDateTo;
    }

    public void setConversionDateTo(Date conversionDateTo) {
        this.conversionDateTo = conversionDateTo;
    }

    public Date getSettlementDateFrom() {
        return settlementDateFrom;
    }

    public void setSettlementDateFrom(Date settlementDateFrom) {
        this.settlementDateFrom = settlementDateFrom;
    }

    public Date getSettlementDateTo() {
        return settlementDateTo;
    }

    public void setSettlementDateTo(Date settlementDateTo) {
        this.settlementDateTo = settlementDateTo;
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
        private Date createdAtFrom;
        private Date createdAtTo;
        private Date updatedAtFrom;
        private Date updatedAtTo;
        private String uniqueRequestId;
        private String scope;

        public String getDescription() {
            return description;
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

        public String getUniqueRequestId() {
            return uniqueRequestId;
        }

        public String getScope() {
            return scope;
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
            map.values().removeIf(Objects::isNull);

            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return String.format("{\"error\": \"%s\"}", e.getMessage());
            }
        }
    }
}
