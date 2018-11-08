package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
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
public class ConversionProfitAndLoss implements Entity {

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
    private Date eventDateTimeFrom;
    private Date eventDateTimeTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String scope;

    protected ConversionProfitAndLoss() {}

    public static ConversionProfitAndLoss create() {
        return new ConversionProfitAndLoss();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public Date getEventDateTimeFrom() {
        return eventDateTimeFrom;
    }

    public void setEventDateTimeFrom(Date eventDateTimeFrom) {
        this.eventDateTimeFrom = eventDateTimeFrom;
    }

    public Date getEventDateTimeTo() {
        return eventDateTimeTo;
    }

    public void setEventDateTimeTo(Date eventDateTimeTo) {
        this.eventDateTimeTo = eventDateTimeTo;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

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
