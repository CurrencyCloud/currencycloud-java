package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        map.put("contactId", contactId);
        map.put("eventAccountId", eventAccountId);
        map.put("eventContactId", eventContactId);
        map.put("conversionId", conversionId);
        map.put("eventType", eventType);
        map.put("amount", amount);
        map.put("currency", currency);
        map.put("notes", notes);
        map.put("eventDateTime", eventDateTime);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
