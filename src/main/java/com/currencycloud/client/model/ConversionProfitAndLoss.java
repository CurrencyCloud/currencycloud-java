package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionProfitAndLoss implements Entity {

    private String id;
    private String accountId;
    private String contactId;
    private String eventAccountId;
    private String eventContactId;
    private String conversionId;
    private String eventType;
    private String externalAmount;
    private String currency;
    private String notes;
    private Date eventDateTime;

    protected ConversionProfitAndLoss() { }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getEventAccountId() {
        return eventAccountId;
    }

    public void setEventAccountId(String eventAccountId) {
        this.eventAccountId = eventAccountId;
    }

    public String getEventContactId() {
        return eventContactId;
    }

    public void setEventContactId(String eventContactId) {
        this.eventContactId = eventContactId;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getExternalAmount() {
        return externalAmount;
    }

    public void setExternalAmount(String externalAmount) {
        this.externalAmount = externalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getAccountId() {

        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("accountId", accountId)
                .appendField("contactId", contactId)
                .appendField("eventAccountId", eventAccountId)
                .appendField("eventContactId", eventContactId)
                .appendField("conversionId", conversionId)
                .appendField("eventType", eventType)
                .appendField("externalAmount", externalAmount)
                .appendField("currency", currency)
                .appendField("notes", notes)
                .appendField("eventDateTime", eventDateTime)
                .toString();
        }
}
