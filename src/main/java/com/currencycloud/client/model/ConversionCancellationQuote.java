package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionCancellationQuote {

    private String floatingCcy;
    private String overallProfitAndLoss;
    private Date eventDateTime;

    protected ConversionCancellationQuote() { }

    public String getFloatingCcy() {
        return floatingCcy;
    }

    public void setFloatingCcy(String floatingCcy) {
        this.floatingCcy = floatingCcy;
    }

    public String getOverallProfitAndLoss() {
        return overallProfitAndLoss;
    }

    public void setOverallProfitAndLoss(String overallProfitAndLoss) {
        this.overallProfitAndLoss = overallProfitAndLoss;
    }

    public Date getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("floatingCcy", floatingCcy)
                .appendField("overallProfitAndLoss", overallProfitAndLoss)
                .appendField("eventDateTime", eventDateTime)
                .toString();
        }
}
