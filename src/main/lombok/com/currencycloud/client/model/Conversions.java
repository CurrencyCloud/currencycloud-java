package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Conversions extends PaginatedData {

    private List<Conversion> conversions;

    @Override
    public String toString() {
        return String.format("{\"conversions\":%s, \"pagination\":%s}", conversions, pagination);
    }
}
