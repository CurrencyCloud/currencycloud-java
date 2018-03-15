package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Currency {

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

    @Override
    public String toString() {
        return String.format("Currency{code='%s', decimalPlaces=%d, name='%s'}", code, decimalPlaces, name);
    }
}
