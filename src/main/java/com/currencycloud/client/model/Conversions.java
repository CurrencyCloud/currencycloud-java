package com.currencycloud.client.model;

import java.util.List;

public class Conversions extends PaginatedData {

    private List<Conversion> conversions;

    public List<Conversion> getConversions() {
        return conversions;
    }

    @Override
    public String toString() {
        return String.format("Conversions{conversions=%s, pagination=%s}", conversions, pagination);
    }
}
