package com.currencycloud.client.model;

import java.util.List;

public class Ibans extends PaginatedData {

    private List<Iban> ibans;

    public List<Iban> getIbans() {
        return ibans;
    }

    @Override
    public String toString() {
        return String.format("{\"ibans\":%s, \"pagination\":%s}", ibans, pagination);
    }
}
