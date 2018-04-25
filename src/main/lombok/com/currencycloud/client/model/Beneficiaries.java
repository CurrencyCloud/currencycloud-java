package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class Beneficiaries extends PaginatedData {

    private List<Beneficiary> beneficiaries;

    @Override
    public String toString() {
        return String.format("{\"beneficiaries\":%s, \"pagination\":%s}", beneficiaries, pagination);
    }
}
