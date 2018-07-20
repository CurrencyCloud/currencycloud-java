package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;
import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionCancellationQuote implements Entity {

    private String id;
    private BigDecimal amount;
    private String currency;
    private Date eventDateTime;

    protected ConversionCancellationQuote() { }

    private ConversionCancellationQuote(String id) {
        this.id = id;
    }

    public static ConversionCancellationQuote create() {
        return new ConversionCancellationQuote();
    }

    public static ConversionCancellationQuote create(String id) {
        return new ConversionCancellationQuote(id);
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
                .appendField("amount", amount)
                .appendField("currency", currency)
                .appendField("eventDateTime", eventDateTime)
                .toString();
        }
}
