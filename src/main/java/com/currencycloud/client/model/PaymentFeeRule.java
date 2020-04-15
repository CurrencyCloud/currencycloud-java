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
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentFeeRule {

    private String paymentFeeId;
    private String paymentFeeName;
    private String paymentType;
    private String chargeType;
    private BigDecimal feeAmount;
    private String feeCurrency;

    public String getPaymentFeeId() { return paymentFeeId; }

    public void setPaymentFeeId(final String paymentFeeId) { this.paymentFeeId = paymentFeeId; }

    public String getPaymentFeeName() { return paymentFeeName; }

    public void setPaymentFeeName(final String paymentFeeName) { this.paymentFeeName = paymentFeeName; }

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

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("paymentFeeId", this.paymentFeeId);
        map.put("paymentFeeName", this.paymentFeeName);
        map.put("paymentType", this.paymentType);
        map.put("chargeType", this.chargeType);
        map.put("feeAmount", this.feeAmount);
        map.put("feeCurrency", this.feeCurrency);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
