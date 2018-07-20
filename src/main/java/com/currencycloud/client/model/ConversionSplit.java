package com.currencycloud.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;
import java.math.BigDecimal;

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
        return new JSONObject()
                .appendField("parentConversion", parentConversion)
                .appendField("childConversion", childConversion)
                .toString();
    }
}
