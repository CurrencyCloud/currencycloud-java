package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDeliveryDate {

      private Date paymentDate;
    private Date paymentDeliveryDate;
    private Date paymentCutoffTime;
    private String paymentType;
    private String currency;
    private String bankCountry;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Date getPaymentDeliveryDate() {
        return paymentDeliveryDate;
    }

    public Date getPaymentCutoffTime() {
        return paymentCutoffTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("paymentDate", paymentDate);
        map.put("paymentDeliveryDate", paymentDeliveryDate);
        map.put("paymentCutoffTime", paymentCutoffTime);
        map.put("paymentType", paymentType);
        map.put("currency", currency);
        map.put("bankCountry", bankCountry);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
