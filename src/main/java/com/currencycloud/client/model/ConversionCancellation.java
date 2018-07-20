package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionCancellation implements Entity {

    private String id;
    private String accountId;
    private String contactId;
    private String eventAccountId;
    private String eventContactId;
    private String conversionId;
    private String eventType;
    private BigDecimal amount;
    private String currency;
    private String notes;
    private Date eventDateTime;

    protected ConversionCancellation() { }

    private ConversionCancellation(String id) {
        this.id = id;
    }

    public static ConversionCancellation create() {
        return new ConversionCancellation();
    }

    public static ConversionCancellation create(String id) {
        return new ConversionCancellation(id);
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public String toString() {
        return new JSONObject()
                .appendField("accountId", accountId)
                .appendField("contactId", contactId)
                .appendField("eventAccountId", eventAccountId)
                .appendField("eventContactId", eventContactId)
                .appendField("conversionId", conversionId)
                .appendField("eventType", eventType)
                .appendField("amount", amount)
                .appendField("currency", currency)
                .appendField("notes", notes)
                .appendField("eventDateTime", eventDateTime)
                .toString();
        }
}
