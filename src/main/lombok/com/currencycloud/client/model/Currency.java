package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Currency {

    private String code;
    private Integer decimalPlaces;
    private String name;
    private Boolean onlineTrading;

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("code", code)
                .appendField("decimalPlaces", decimalPlaces)
                .appendField("name", name)
                .appendField("onlineTrading", onlineTrading)
                .toString();
    }
}
