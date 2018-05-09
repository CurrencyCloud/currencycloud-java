package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateChangeDetails {

    private Date initialValueDate;
    private Date currentValueDate;
    private Date initialDeliveryDate;
    private Date currentDeliveryDate;
    private String totalProfitAndLoss;
    private String floatingCcy;
    private List<Change> changes;

    protected DateChangeDetails() {
        this.changes = new ArrayList<>();
    }

    public Date getInitialValueDate() {
        return initialValueDate;
    }

    public void setInitialValueDate(Date initialValueDate) {
        this.initialValueDate = initialValueDate;
    }

    public Date getCurrentValueDate() {
        return currentValueDate;
    }

    public void setCurrentValueDate(Date currentValueDate) {
        this.currentValueDate = currentValueDate;
    }

    public Date getInitialDeliveryDate() {
        return initialDeliveryDate;
    }

    public void setInitialDeliveryDate(Date initialDeliveryDate) {
        this.initialDeliveryDate = initialDeliveryDate;
    }

    public Date getCurrentDeliveryDate() {
        return currentDeliveryDate;
    }

    public void setCurrentDeliveryDate(Date currentDeliveryDate) {
        this.currentDeliveryDate = currentDeliveryDate;
    }

    public String getTotalProfitAndLoss() {
        return totalProfitAndLoss;
    }

    public void setTotalProfitAndLoss(String totalProfitAndLoss) {
        this.totalProfitAndLoss = totalProfitAndLoss;
    }

    public String getFloatingCcy() {
        return floatingCcy;
    }

    public void setFloatingCcy(String floatingCcy) {
        this.floatingCcy = floatingCcy;
    }

    public List<Change> getChanges() {
        return changes;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("floatingCcy", floatingCcy)
                .appendField("totalProfitAndLoss", totalProfitAndLoss)
                .appendField("currentDeliveryDate", currentDeliveryDate)
                .appendField("currentValueDate", currentValueDate)
                .appendField("initialDeliveryDate", initialDeliveryDate)
                .appendField("initialValueDate", initialValueDate)
                .appendField("changes", changes)
                .toString();
    }

}

class Change {
    private Date requestedValueDate;
    private Date newValueDate;
    private Date newDeliveryDate;
    private String profitAndLoss;
    private String adminFee;
    private String type;
    private String status;

    public Change() {

    }

    public Date getRequestedValueDate() {
        return requestedValueDate;
    }

    public void setRequestedValueDate(Date requestedValueDate) {
        this.requestedValueDate = requestedValueDate;
    }

    public Date getNewValueDate() {
        return newValueDate;
    }

    public void setNewValueDate(Date newValueDate) {
        this.newValueDate = newValueDate;
    }

    public Date getNewDeliveryDate() {
        return newDeliveryDate;
    }

    public void setNewDeliveryDate(Date newDeliveryDate) {
        this.newDeliveryDate = newDeliveryDate;
    }

    public String getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(String profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public String getAdminFee() {
        return adminFee;
    }

    public void setAdminFee(String adminFee) {
        this.adminFee = adminFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("requestedValueDate", requestedValueDate)
                .appendField("newValueDate", newValueDate)
                .appendField("newDeliveryDate", newDeliveryDate)
                .appendField("profitAndLoss", profitAndLoss)
                .appendField("adminFee", adminFee)
                .appendField("type", type)
                .appendField("status", status)
                .toString();
    }
}
