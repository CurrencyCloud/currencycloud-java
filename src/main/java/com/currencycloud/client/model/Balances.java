package com.currencycloud.client.model;

import java.util.List;

public class Balances extends PaginatedData {

    private List<Balance> balances;

    public List<Balance> getBalances() {
        return balances;
    }

    @Override
    public String toString() {
        return String.format("{\"balances\":%s, \"pagination\":%s}", balances, pagination);
    }
}
