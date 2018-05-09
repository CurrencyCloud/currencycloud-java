package com.currencycloud.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionSplitHistory {

    private ConversionSplitDetails parentConversion;
    private ConversionSplitDetails originConversion;
    private List<ConversionSplitDetails> childConversions;

    protected ConversionSplitHistory() {
        childConversions = new ArrayList<>();
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
                .appendField("parent_conversion", parentConversion)
                .appendField("origin_conversion", originConversion)
                .appendField("child_conversions", childConversions)
                .toString();
    }
}
