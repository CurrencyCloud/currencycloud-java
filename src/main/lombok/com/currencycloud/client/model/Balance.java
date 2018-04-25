package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Balance implements Entity {

    private String id;

    private String accountId;

    private String currency;

    private BigDecimal amount;

    private Date createdAt;

    private Date updatedAt;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private Date asAtDate;
    private String scope;

    protected Balance() { }

    public static Balance create() {
        return new Balance();
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("accountId", accountId)
                .appendField("currency", currency)
                .appendField("amount", amount)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
    }
}
