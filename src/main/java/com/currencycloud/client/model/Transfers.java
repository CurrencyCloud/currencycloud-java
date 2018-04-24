package com.currencycloud.client.model;

import java.util.List;

public class Transfers extends PaginatedData {

    private List<Transfer> transfers;

    public List<Transfer> getTransfers() {
        return transfers;
    }

    @Override
    public String toString() {
        return String.format("{\"transfers\":%s, \"pagination\":%s}", transfers, pagination);
    }
}
