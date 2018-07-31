package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Transactions extends PaginatedData {

    private List<Transaction> transactions;

    @Override
    public String toString() {
        return String.format("{\"transactions\":%s, \"pagination\":%s}", transactions, pagination);
    }
}
