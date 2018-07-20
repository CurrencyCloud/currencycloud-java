package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionDateChange implements Entity {

    private String id;
    private String conversionId;
    private BigDecimal amount;
    private String currency;
    private Date newConversionDate;
    private Date newSettlementDate;
    private Date oldConversionDate;
    private Date oldSettlementDate;
    private Date eventDateTime;

    protected ConversionDateChange() { }

    private ConversionDateChange(String id, Date newSettlementDate) {
        this.id = id;
        this.newSettlementDate = newSettlementDate;
    }

    public static ConversionDateChange create() {
        return new ConversionDateChange();
    }

    public static ConversionDateChange create(String id, Date newSettlementDate) {
        return new ConversionDateChange(id, newSettlementDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getNewConversionDate() {
        return newConversionDate;
    }

    public void setNewConversionDate(Date newConversionDate) {
        this.newConversionDate = newConversionDate;
    }

    public Date getNewSettlementDate() {
        return newSettlementDate;
    }

    public void setNewSettlementDate(Date newSettlementDate) {
        this.newSettlementDate = newSettlementDate;
    }

    public Date getOldConversionDate() {
        return oldConversionDate;
    }

    public void setOldConversionDate(Date oldConversionDate) {
        this.oldConversionDate = oldConversionDate;
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
                .appendField("conversionId", conversionId)
                .appendField("amount", amount)
                .appendField("currency", currency)
                .appendField("newConversionDate", newConversionDate)
                .appendField("newSettlementDate", newSettlementDate)
                .appendField("oldConversionDate", oldConversionDate)
                .appendField("oldSettlementDate", oldSettlementDate)
                .appendField("eventDateTime", eventDateTime)
                .toString();
        }
}
