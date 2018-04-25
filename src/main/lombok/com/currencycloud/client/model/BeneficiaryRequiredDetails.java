package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class BeneficiaryRequiredDetails {

    private List<Map<String, String>> details;

    public List<Map<String, String>> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("{\"details\":%s}", details);
    }
}
