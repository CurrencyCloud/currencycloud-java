package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundingTransaction implements Entity {

    private String id;
    private String amount;
    private String currency;
    private String rail;
    private String additionalInformation;
    private String receivingAccountRoutingCode;
    private Date valueDate;
    private String receivingAccountNumber;
    private String receivingAccountIban;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;
    private SenderInformation sender;

    protected FundingTransaction() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRail() {
        return rail;
    }

    public void setRail(String rail) {
        this.rail = rail;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getReceivingAccountRoutingCode() {
        return receivingAccountRoutingCode;
    }

    public void setReceivingAccountRoutingCode(String receivingAccountRoutingCode) {
        this.receivingAccountRoutingCode = receivingAccountRoutingCode;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getReceivingAccountNumber() {
        return receivingAccountNumber;
    }

    public void setReceivingAccountNumber(String receivingAccountNumber) {
        this.receivingAccountNumber = receivingAccountNumber;
    }

    public String getReceivingAccountIban() {
        return receivingAccountIban;
    }

    public void setReceivingAccountIban(String receivingAccountIban) {
        this.receivingAccountIban = receivingAccountIban;
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

    public SenderInformation getSender() {
        return sender;
    }

    public void setSender(SenderInformation sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("amount", amount);
        map.put("currency", currency);
        map.put("rail", rail);
        map.put("additionalInformation", additionalInformation);
        map.put("receivingAccountRoutingCode", receivingAccountRoutingCode);
        map.put("valueDate", valueDate);
        map.put("receivingAccountNumber", receivingAccountNumber);
        map.put("receivingAccountIban", receivingAccountIban);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("completedAt", completedAt);
        map.put("sender", sender);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SenderInformation {

        @JsonProperty("sender_account_number")
        private String accountNumber;

        @JsonProperty("sender_address")
        private String address;

        @JsonProperty("sender_bic")
        private String bic;

        @JsonProperty("sender_country")
        private String country;

        @JsonProperty("sender_iban")
        private String iban;

        @JsonProperty("sender_id")
        private String id;

        @JsonProperty("sender_name")
        private String name;

        @JsonProperty("sender_routing_code")
        private String routingCode;

        protected SenderInformation() {}

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBic() {
            return bic;
        }

        public void setBic(String bic) {
            this.bic = bic;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoutingCode() {
            return routingCode;
        }

        public void setRoutingCode(String routingCode) {
            this.routingCode = routingCode;
        }
    }
}
