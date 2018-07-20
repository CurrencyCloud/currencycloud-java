package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConversionProfitAndLosses extends PaginatedData {

    @JsonProperty("conversion_profit_and_losses")
    private List<ConversionProfitAndLoss> conversionProfitAndLosses;

    public List<ConversionProfitAndLoss> getConversionProfitAndLosses() {
        return conversionProfitAndLosses;
    }

    @Override
    public String toString() {
        return String.format("{\"conversion_profit_and_losses\":%s, \"pagination\":%s}", conversionProfitAndLosses, pagination);
    }
}
