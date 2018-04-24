package com.currencycloud.client.model;

import java.util.List;

public class Settlements extends PaginatedData {

    private List<Settlement> settlements;

    public List<Settlement> getSettlements() {
        return settlements;
    }

    @Override
    public String toString() {
        return String.format("{\"settlements\":%s, \"pagination\":%s}", settlements, pagination);
    }
}
