package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.util.Date;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConversionDates {

    private Date firstConversionDate;

    private Date defaultConversionDate;

    private Map<Date, String> invalidConversionDates;

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("firstConversionDate", firstConversionDate)
                .appendField("defaultConversionDate", defaultConversionDate)
                .appendField("invalidConversionDates", invalidConversionDates)
                .toString();
    }
}
