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
public class ConversionSplit implements Entity {

    private String id;
    private BigDecimal amount;
    private ConversionSplitDetails parentConversion;
    private ConversionSplitDetails childConversion;

    protected ConversionSplit() {

    }

    private ConversionSplit(String id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public static ConversionSplit create() {
        return new ConversionSplit();
    }

    public static ConversionSplit create(String id, BigDecimal amount) {
        return new ConversionSplit(id, amount);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ConversionSplitDetails getParentConversion() {
        return parentConversion;
    }

    public void setParentConversion(ConversionSplitDetails parentConversion) {
        this.parentConversion = parentConversion;
    }

    public ConversionSplitDetails getChildConversion() {
        return childConversion;
    }

    public void setChildConversion(ConversionSplitDetails childConversion) {
        this.childConversion = childConversion;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("parentConversion", parentConversion);
        map.put("childConversion", childConversion);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
