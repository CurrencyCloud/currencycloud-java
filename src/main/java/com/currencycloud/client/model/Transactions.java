package com.currencycloud.client.model;

import java.util.List;

public class Transactions extends PaginatedData {

    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return String.format("{\"transactions\":%s, \"pagination\":%s}", transactions, pagination);
    }
}
