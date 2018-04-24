package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VirtualAccounts extends PaginatedData {

    private List<VirtualAccount> virtualAccounts;

    public List<VirtualAccount> getVirtualAccounts() {
        return virtualAccounts;
    }

    @Override
    public String toString() {
        return String.format("{\"virtual_accounts\":%s, \"pagination\":%s}", virtualAccounts, pagination);
    }
}
