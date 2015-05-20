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

    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Currency {

        private String code;
        private Integer decimalPlaces;
        private String name;

        public String getCode() {
            return code;
        }

        public Integer getDecimalPlaces() {
            return decimalPlaces;
        }

        public String getName() {
            return name;
        }
    }
}
