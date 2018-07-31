package com.currencycloud.client.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Transfers extends PaginatedData {

    private List<Transfer> transfers;

    @Override
    public String toString() {
        return String.format("{\"transfers\":%s, \"pagination\":%s}", transfers, pagination);
    }
}
