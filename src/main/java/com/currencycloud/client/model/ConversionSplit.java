package com.currencycloud.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionSplit {

    private ConversionSplitDetails parentConversion;
    private ConversionSplitDetails childConversion;

    protected ConversionSplit() {

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
        return new JSONObject()
                .appendField("parent_conversion", parentConversion)
                .appendField("child_conversion", childConversion)
                .toString();
    }
}
