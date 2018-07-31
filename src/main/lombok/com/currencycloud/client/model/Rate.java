package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Rate {

    private BigDecimal bid;

    private BigDecimal offer;

    private Rate(BigDecimal bid, BigDecimal offer) {
        this.bid = bid;
        this.offer = offer;
    }

    public static Rate create(List<BigDecimal> bidOffer) {
        return new Rate(bidOffer.get(0), bidOffer.get(1));
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("bid", bid)
                .appendField("offer", offer)
                .toString();
    }
}
