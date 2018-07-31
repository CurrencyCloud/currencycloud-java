package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Rates {

    @JsonProperty
    private Map<String, List<BigDecimal>> rates;
    @JsonProperty
    private List<String> unavailable;

    public Rate getRate(String currencyPair) {
        return Rate.create(rates.get(currencyPair));
    }

    public Collection<String> getCurrencyPairs() {
        return rates.keySet();
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("rates", rates)
                .appendField("unavailable", unavailable)
                .toString();
    }
}
