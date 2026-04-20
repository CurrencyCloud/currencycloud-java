package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompleteCollectionsScreeningResponse {

    private String transactionId;
    private String accountId;
    private String houseAccountId;
    private Result result;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHouseAccountId() {
        return houseAccountId;
    }

    public void setHouseAccountId(String houseAccountId) {
        this.houseAccountId = houseAccountId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<>();
        map.put("transactionId", transactionId);
        map.put("accountId", accountId);
        map.put("houseAccountId", houseAccountId);
        map.put("result", result);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Result {
        private String reason;
        private Boolean accepted;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Boolean getAccepted() {
            return accepted;
        }

        public void setAccepted(Boolean accepted) {
            this.accepted = accepted;
        }

        @Override
        public String toString() {
            final ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<>();
            map.put("reason", reason);
            map.put("accepted", accepted);

            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return String.format("{\"error\": \"%s\"}", e.getMessage());
            }
        }
    }
}
