package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundingAccounts extends PaginatedData {

    private List<FundingAccount> fundingAccounts;

    public List<FundingAccount> getFundingAccounts() {
        return fundingAccounts;
    }

    @Override
    public String toString() {
        return String.format("{\"fundingAccounts\":%s, \"pagination\":%s}", fundingAccounts, pagination);
    }
}