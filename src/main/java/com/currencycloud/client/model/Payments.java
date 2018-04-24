package com.currencycloud.client.model;

import java.util.List;

public class Payments extends PaginatedData {

    private List<Payment> payments;

    public List<Payment> getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return String.format("{\"payments\":%s, \"pagination\":%s}", payments, pagination);
    }
}
