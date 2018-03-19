package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class Balances extends PaginatedData {

    private List<Balance> balances;

}
