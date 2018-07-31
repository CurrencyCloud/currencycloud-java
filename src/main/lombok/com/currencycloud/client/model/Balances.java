package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Balances extends PaginatedData {

    private List<Balance> balances;

    @Override
    public String toString() {
        return String.format("{\"balances\":%s, \"pagination\":%s}", balances, pagination);
    }

}
