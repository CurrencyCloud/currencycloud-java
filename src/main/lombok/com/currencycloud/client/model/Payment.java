package com.currencycloud.client.model;


import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Payment.class)
@Data
public class Payment implements Entity {

    private String id;
    private String shortReference;
    private String beneficiaryId;
    private String conversionId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String paymentType;
    private String reference;
    private String reason;
    private Date paymentDate;
    private Date transferredAt;
    private Integer authorisationStepsRequired;
    private String creatorContactId;
    private String lastUpdaterContactId;
    private String failureReason;
    private String payerId;
    private Date createdAt;
    private Date updatedAt;
    private String uniqueRequestId;

    protected Payment() { }

    private Payment(String id,
                    String currency,
                    String beneficiaryId,
                    BigDecimal amount,
                    String reason,
                    String reference,
                    String conversionId,
                    Date paymentDate,
                    String paymentType,
                    String shortReference,
                    String status,
                    @Nullable String uniqueRequestId
    ) {
        this.id = id;
        this.currency = currency;
        this.beneficiaryId = beneficiaryId;
        this.amount = amount;
        this.reason = reason;
        this.reference = reference;
        this.conversionId = conversionId;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.shortReference = shortReference;
        this.status = status;
        this.uniqueRequestId = uniqueRequestId;
    }

    public static Payment create() {
        return new Payment();
    }

    /** Creates a new {@link Payment} that can be used as a parameter for the
     * {@link com.currencycloud.client.CurrencyCloudClient#createPayment(Payment, Payer)} method.
     */
    public static Payment create(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String reference,
            @Nullable Date paymentDate,
            @Nullable String paymentType,
            @Nullable String conversionId,
            @Nullable String uniqueRequestId
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, reference, conversionId, paymentDate, paymentType, null, null, uniqueRequestId);
    }

    /** Creates a new {@link Payment} that can be used a as parameter for the
     * {@link com.currencycloud.client.CurrencyCloudClient#findPayments} method.
     */
    public static Payment createExample(
            String currency,
            String beneficiaryId,
            BigDecimal amount,
            String reason,
            String conversionId,
            String shortReference,
            String status,
            @Nullable String uniqueRequestId
    ) {
        return new Payment(null, currency, beneficiaryId, amount, reason, null, conversionId, null, null, shortReference, status, uniqueRequestId);
    }

}
