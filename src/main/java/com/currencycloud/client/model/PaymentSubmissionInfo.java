package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentSubmissionInfo {

    private String status;
    private String submissionRef;
    private String format;
    private String message;

    protected PaymentSubmissionInfo() { }

    private PaymentSubmissionInfo(String status,
                              String submissionRef,
                              String format,
                              String message) {
        this.status = status;
        this.submissionRef = submissionRef;
        this.format = format;
        this.message = message;
    }

    public static PaymentSubmissionInfo create() {
        return new PaymentSubmissionInfo();
    }

    /** Creates a new {@link PaymentSubmissionInfo} that can be used as a return value for the
     * {@link com.currencycloud.client.CurrencyCloudClient#retrievePaymentSubmissionInfo(String)} method.
     */
    public static PaymentSubmissionInfo create(
            String status,
            String submissionRef,
            String format,
            String message
    ) {
        return new PaymentSubmissionInfo(status, submissionRef, message, format);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmissionRef() {
        return submissionRef;
    }

    public void setSubmissionRef(String submissionRef) {
        this.submissionRef = submissionRef;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("submissionRef", submissionRef);
        map.put("format", format);
        map.put("message", message);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
