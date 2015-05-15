package com.currencycloud.client.model;

import java.util.List;

public class Accounts extends PaginatedData {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }
}
