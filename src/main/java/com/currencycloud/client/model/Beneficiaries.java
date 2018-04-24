package com.currencycloud.client.model;

import java.util.List;

public class Beneficiaries extends PaginatedData {

    private List<Beneficiary> beneficiaries;

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    @Override
    public String toString() {
        return String.format("{\"beneficiaries\":%s, \"pagination\":%s}", beneficiaries, pagination);
    }
}
