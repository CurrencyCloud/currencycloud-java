package com.currencycloud.client.model;

import java.util.List;

public class BeneficiariesData extends PaginatedData {

    private List<Beneficiary> beneficiaries;

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }
}
