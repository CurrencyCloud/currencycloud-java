package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawalAccounts extends PaginatedData {

    private List<WithdrawalAccount> withdrawalAccounts;

    public List<WithdrawalAccount> getWithdrawalAccounts() {
        return withdrawalAccounts;
    }

    @Override
    public String toString() {
        return String.format("{\"withdrawal_accounts\":%s, \"pagination\":%s}", withdrawalAccounts, pagination);
    }
}


