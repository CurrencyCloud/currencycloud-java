package com.currencycloud.client.model;

import java.util.List;

public class Accounts extends PaginatedData {

    private List<Account> beneficiaries;

    public List<Account> getBeneficiaries() {
        return beneficiaries;
    }
}
