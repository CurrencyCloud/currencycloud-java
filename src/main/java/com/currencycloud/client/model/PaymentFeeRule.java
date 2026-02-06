package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentFeeRule {

    private String paymentType;
    private String chargeType;
    private BigDecimal feeAmount;
    private String feeCurrency;
    private String paymentFeeId;
    private String paymentFeeName;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void SetFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public String getPaymentFeeId() {
        return paymentFeeId;
    }

    public void setPaymentFeeId(String paymentFeeId) {
        this.paymentFeeId = paymentFeeId;
    }

    public String getPaymentFeeName() {
        return paymentFeeName;
    }

    public void setPaymentFeeName(String paymentFeeName) {
        this.paymentFeeName = paymentFeeName;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("paymentType", paymentType);
        map.put("chargeType", chargeType);
        map.put("feeAmount", feeAmount);
        map.put("feeCurrency", feeCurrency);
        map.put("paymentFeeId", paymentFeeId);
        map.put("paymentFeeName", paymentFeeName);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
