package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionDateChangeDetails implements Entity {

    private String id;
    private Date initialValueDate;
    private Date currentValueDate;
    private Date initialDeliveryDate;
    private Date currentDeliveryDate;
    private BigDecimal totalProfitAndLoss;
    private String floatingCurrency;
    @JsonProperty("changes")
    private List<DateChange> dateChanges;

    protected ConversionDateChangeDetails() {
        this.dateChanges = new ArrayList<>();
    }

    private ConversionDateChangeDetails(String id) {
        this.id = id;
        this.dateChanges = new ArrayList<>();
    }

    public static ConversionDateChangeDetails create() {
        return new ConversionDateChangeDetails();
    }

    public static ConversionDateChangeDetails create(String id) {
        return new ConversionDateChangeDetails(id);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInitialValueDate() {
        return initialValueDate;
    }

    public void setInitialValueDate(Date initialValueDate) {
        this.initialValueDate = initialValueDate;
    }

    public Date getCurrentValueDate() {
        return currentValueDate;
    }

    public void setCurrentValueDate(Date currentValueDate) {
        this.currentValueDate = currentValueDate;
    }

    public Date getInitialDeliveryDate() {
        return initialDeliveryDate;
    }

    public void setInitialDeliveryDate(Date initialDeliveryDate) {
        this.initialDeliveryDate = initialDeliveryDate;
    }

    public Date getCurrentDeliveryDate() {
        return currentDeliveryDate;
    }

    public void setCurrentDeliveryDate(Date currentDeliveryDate) {
        this.currentDeliveryDate = currentDeliveryDate;
    }

    public BigDecimal getTotalProfitAndLoss() {
        return totalProfitAndLoss;
    }

    public void setTotalProfitAndLoss(BigDecimal totalProfitAndLoss) {
        this.totalProfitAndLoss = totalProfitAndLoss;
    }

    public String getFloatingCurrency() {
        return floatingCurrency;
    }

    public void setFloatingCurrency(String floatingCurrency) {
        this.floatingCurrency = floatingCurrency;
    }

    public List<DateChange> getDateChanges() {
        return dateChanges;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("initialValueDate", initialValueDate);
        map.put("currentValueDate", currentValueDate);
        map.put("initialDeliveryDate", initialDeliveryDate);
        map.put("currentDeliveryDate", currentDeliveryDate);
        map.put("totalProfitAndLoss", totalProfitAndLoss);
        map.put("floatingCurrency", floatingCurrency);
        map.put("dateChanges", dateChanges);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DateChange {
        private Date requestedValueDate;
        private Date newValueDate;
        private Date newDeliveryDate;
        private BigDecimal profitAndLoss;
        private BigDecimal adminFee;
        private String type;
        private String status;

        private DateChange() { }

        public Date getRequestedValueDate() {
            return requestedValueDate;
        }

        public void setRequestedValueDate(Date requestedValueDate) {
            this.requestedValueDate = requestedValueDate;
        }

        public Date getNewValueDate() {
            return newValueDate;
        }

        public void setNewValueDate(Date newValueDate) {
            this.newValueDate = newValueDate;
        }

        public Date getNewDeliveryDate() {
            return newDeliveryDate;
        }

        public void setNewDeliveryDate(Date newDeliveryDate) {
            this.newDeliveryDate = newDeliveryDate;
        }

        public BigDecimal getProfitAndLoss() {
            return profitAndLoss;
        }

        public void setProfitAndLoss(BigDecimal profitAndLoss) {
            this.profitAndLoss = profitAndLoss;
        }

        public BigDecimal getAdminFee() {
            return adminFee;
        }

        public void setAdminFee(BigDecimal adminFee) {
            this.adminFee = adminFee;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            final ObjectMapper objectMapper = new ObjectMapper()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

            Map<String, Object> map = new HashMap<>();
            map.put("requestedValueDate", requestedValueDate);
            map.put("newValueDate", newValueDate);
            map.put("newDeliveryDate", newDeliveryDate);
            map.put("profitAndLoss", profitAndLoss);
            map.put("adminFee", adminFee);
            map.put("type", type);
            map.put("status", status);

            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return String.format("{\"error\": \"%s\"}", e.getMessage());
            }
        }
    }
}
