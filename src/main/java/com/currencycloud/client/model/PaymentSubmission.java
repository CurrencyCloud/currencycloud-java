package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentSubmission {

    private String status;
    private String submissionRef;
    private String mt103;

    protected PaymentSubmission() { }

    private PaymentSubmission(String status,
                              String submissionRef,
                              String mt103) {
        this.status = status;
        this.submissionRef = submissionRef;
        this.mt103 = mt103;
    }

    public static PaymentSubmission create() {
        return new PaymentSubmission();
    }

    /** Creates a new {@link PaymentSubmission} that can be used as a return value for the
     * {@link com.currencycloud.client.CurrencyCloudClient#retrievePaymentSubmission(String)} method.
     */
    public static PaymentSubmission create(
            String status,
            String submissionRef,
            String mt103
    ) {
        return new PaymentSubmission(status, submissionRef, mt103);
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

    public String getMt103() {
        return mt103;
    }

    public void setMt103(String mt103) {
        this.mt103 = mt103;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("status", status)
                .appendField("submissionRef", submissionRef)
                .appendField("mt103", mt103)
                .toString();
        }
}
