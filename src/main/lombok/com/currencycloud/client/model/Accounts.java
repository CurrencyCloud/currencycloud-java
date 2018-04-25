package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class Accounts extends PaginatedData {

    private List<Account> accounts;

    @Override
    public String toString() {
        return String.format("{\"accounts\":%s, \"pagination\":%s}", accounts, pagination);
    }
}
