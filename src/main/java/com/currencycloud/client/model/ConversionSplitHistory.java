package com.currencycloud.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.List;

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
        return new JSONObject()
                .appendField("parentConversion", parentConversion)
                .appendField("originConversion", originConversion)
                .appendField("childConversions", childConversions)
                .toString();
    }
}
