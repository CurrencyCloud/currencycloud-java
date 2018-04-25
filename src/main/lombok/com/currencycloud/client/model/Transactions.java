package com.currencycloud.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class Transactions extends PaginatedData {

    private List<Transaction> transactions;

    @Override
    public String toString() {
        return String.format("{\"transactions\":%s, \"pagination\":%s}", transactions, pagination);
    }
}
