package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Currencies {

    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    @Override
    public String toString() {
        return String.format("Currencies{currencies=%s}", currencies);
    }
}
