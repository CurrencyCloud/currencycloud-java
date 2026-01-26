package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementAccounts {

    private List<SettlementAccount> settlementAccounts;

    public List<SettlementAccount> getSettlementAccounts() {
        return settlementAccounts;
    }

    @Override
    public String toString() {
        return String.format("{\"settlementAccounts\":%s}", settlementAccounts);
    }
}
