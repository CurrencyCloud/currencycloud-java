package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerRequiredDetail {

    private String payerEntityType;
    private String paymentType;
    private List<Map<String, String>> requiredFields = new ArrayList<>();
    private String payerIdentificationType;

    public String getPayerEntityType() {
        return payerEntityType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public List<Map<String, String>> getRequiredFields() {
        return requiredFields;
    }

    public String getPayerIdentificationType() {
        return payerIdentificationType;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("payerEntityType", payerEntityType);
        map.put("paymentType", paymentType);
        map.put("requiredFields", requiredFields);
        map.put("payerIdentificationType", payerIdentificationType);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
