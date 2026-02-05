package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionDates {

    private Date firstConversionDate;

    private Date defaultConversionDate;

    private Date firstConversionCutoffDatetime;

    private Date optimizeLiquidityConversionDate;

    private Date nextDayConversionDate;

    private Map<Date, String> invalidConversionDates;

    private List<Date> offlineConversionDates;

    public Date getFirstConversionDate() {
        return firstConversionDate;
    }

    public Date getDefaultConversionDate() {
        return defaultConversionDate;
    }

    public Date getFirstConversionCutoffDatetime() { return firstConversionCutoffDatetime; }

    public Date getOptimizeLiquidityConversionDate() { return optimizeLiquidityConversionDate; }

    public Date getNextDayConversionDate() {
        return nextDayConversionDate;
    }

    public Map<Date, String> getInvalidConversionDates() {
        return invalidConversionDates;
    }

    public List<Date> getOfflineConversionDates() {
        return offlineConversionDates;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("firstConversionDate", firstConversionDate);
        map.put("defaultConversionDate", defaultConversionDate);
        map.put("firstConversionCutoffDatetime", firstConversionCutoffDatetime);
        map.put("optimizeLiquidityConversionDate", optimizeLiquidityConversionDate);
        map.put("invalidConversionDates", invalidConversionDates);
        map.put("offlineConversionDates", offlineConversionDates);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
