package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
@EqualsAndHashCode(callSuper = false)
@Getter
public class Payments extends PaginatedData {

    private List<Payment> payments;

    @Override
    public String toString() {
        return String.format("{\"payments\":%s, \"pagination\":%s}", payments, pagination);
    }

}
