package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateChange {

    private String id;
    private String profitLoss;
    private String profitLossCurrency;
    private Date newDeliveryDate;
    private Date newSettlementDate;
    private Date oldDeliveryDate;
    private Date oldSettlementDate;
    private Date eventDateTime;

    protected DateChange() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(String profitLoss) {
        this.profitLoss = profitLoss;
    }

    public String getProfitLossCurrency() {
        return profitLossCurrency;
    }

    public void setProfitLossCurrency(String profitLossCurrency) {
        this.profitLossCurrency = profitLossCurrency;
    }

    public Date getNewDeliveryDate() {
        return newDeliveryDate;
    }

    public void setNewDeliveryDate(Date newDeliveryDate) {
        this.newDeliveryDate = newDeliveryDate;
    }

    public Date getNewSettlementDate() {
        return newSettlementDate;
    }

    public void setNewSettlementDate(Date newSettlementDate) {
        this.newSettlementDate = newSettlementDate;
    }

    public Date getOldDeliveryDate() {
        return oldDeliveryDate;
    }

    public void setOldDeliveryDate(Date oldDeliveryDate) {
        this.oldDeliveryDate = oldDeliveryDate;
    }

    public Date getOldSettlementDate() {
        return oldSettlementDate;
    }

    public void setOldSettlementDate(Date oldSettlementDate) {
        this.oldSettlementDate = oldSettlementDate;
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
                .appendField("id", id)
                .appendField("profitLoss", profitLoss)
                .appendField("profitLossCurrency", profitLossCurrency)
                .appendField("eventDatTime", eventDateTime)
                .appendField("newSettlementDate", newSettlementDate)
                .appendField("newDeliveryDate", newDeliveryDate)
                .appendField("oldDeliveryDate", oldDeliveryDate)
                .appendField("oldSettlementDate", oldSettlementDate)
                .toString();
        }
}
