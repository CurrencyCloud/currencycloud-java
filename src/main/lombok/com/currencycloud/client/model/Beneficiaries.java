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

}
