package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDates {

    private Date firstPaymentDate;

    private Map<Date, String> invalidPaymentDates;

    public Date getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public Map<Date, String> getInvalidPaymentDates() {
        return invalidPaymentDates;
    }

    @Override
    public String toString() {
        return String.format("PaymentDates{firstPaymentDate=%s, invalidPaymentDates=%s}", firstPaymentDate, invalidPaymentDates);
    }
}
