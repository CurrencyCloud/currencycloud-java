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
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentFee {

    private String id;
    private String name;
    private String currency;
    private BigDecimal regularAmount;
    private BigDecimal prioritySharedAmount;
    private BigDecimal priorityOursAmount;
    private String ownerAccountId;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRegularAmount() {
        return regularAmount;
    }

    public void setRegularAmount(BigDecimal regularAmount) {
        this.regularAmount = regularAmount;
    }

    public BigDecimal getPrioritySharedAmount() {
        return prioritySharedAmount;
    }

    public void setPrioritySharedAmount(BigDecimal prioritySharedAmount) {
        this.prioritySharedAmount = prioritySharedAmount;
    }

    public BigDecimal getPriorityOursAmount() {
        return priorityOursAmount;
    }

    public void setPriorityOursAmount(BigDecimal priorityOursAmount) {
        this.priorityOursAmount = priorityOursAmount;
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("currency", currency);
        map.put("regularAmount", regularAmount);
        map.put("prioritySharedAmount", prioritySharedAmount);
        map.put("priorityOursAmount", priorityOursAmount);
        map.put("ownerAccountId", ownerAccountId);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
