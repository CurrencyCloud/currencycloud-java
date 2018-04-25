package com.currencycloud.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Conversions extends PaginatedData {

    private List<Conversion> conversions;

    @Override
    public String toString() {
        return String.format("{\"conversions\":%s, \"pagination\":%s}", conversions, pagination);
    }
}
