package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionSplitHistory implements Entity {

    private String id;
    private ConversionSplitDetails parentConversion;
    private ConversionSplitDetails originConversion;
    private List<ConversionSplitDetails> childConversions;

    protected ConversionSplitHistory() {

    }

    private ConversionSplitHistory(String id) {
        this.id = id;
    }

    public static ConversionSplitHistory create() {
        return new ConversionSplitHistory();
    }

    public static ConversionSplitHistory create(String id) {
        return new ConversionSplitHistory(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConversionSplitDetails getParentConversion() {
        return parentConversion;
    }

    public void setParentConversion(ConversionSplitDetails parentConversion) {
        this.parentConversion = parentConversion;
    }

    public ConversionSplitDetails getOriginConversion() {
        return originConversion;
    }

    public void setOriginConversion(ConversionSplitDetails originConversion) {
        this.originConversion = originConversion;
    }

    public List<ConversionSplitDetails> getChildConversions() {
        return childConversions;
    }

    public void setChildConversions(List<ConversionSplitDetails> childConversions) {
        this.childConversions = childConversions;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("parentConversion", parentConversion);
        map.put("originConversion", originConversion);
        map.put("childConversions", childConversions);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
