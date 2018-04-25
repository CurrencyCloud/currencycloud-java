package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import net.minidev.json.JSONObject;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Transfer implements Entity {

    private String id;
    private String shortReference;
    private String sourceAccountId;
    private String destinationAccountId;
    private String currency;
    private BigDecimal amount;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;
    private String creatorAccountId;
    private String creatorContactId;
    private String reason;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Date completedAtFrom;
    private Date completedAtTo;

    protected Transfer() { }

    private Transfer(String sourceAccountId,
                     String destinationAccountId,
                     String currency,
                     BigDecimal amount,
                     @Nullable String reason
    ) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.currency = currency;
        this.amount = amount;
        this.reason = reason;
    }

    public static Transfer create() {
        return new Transfer();
    }

    /**
     * Creates a transfer as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createTransfer(Transfer)} method,
     * using only required parameters.
     */

    public static Transfer create(String sourceAccountId,
                                  String destinationAccountId,
                                  String currency,
                                  BigDecimal amount) {
        return new Transfer(sourceAccountId, destinationAccountId, currency, amount, null);

    }

    /**
     * Creates a transfer as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createTransfer(Transfer)} method,
     * using all parameters.
     */

    public static Transfer create(String sourceAccountId,
                                  String destinationAccountId,
                                  String currency,
                                  BigDecimal amount,
                                  @Nullable String reason) {
        return new Transfer(sourceAccountId, destinationAccountId, currency, amount, reason);

    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("shortReference", shortReference)
                .appendField("sourceAccountId", sourceAccountId)
                .appendField("destinationAccountId", destinationAccountId)
                .appendField("currency", currency)
                .appendField("amount", amount)
                .appendField("status", status)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .appendField("completedAt", completedAt)
                .appendField("creatorAccountId", creatorAccountId)
                .appendField("creatorContactId", creatorContactId)
                .appendField("reason", reason)
                .toString();
    }
}
