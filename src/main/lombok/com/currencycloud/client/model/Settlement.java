package com.currencycloud.client.model;

import com.currencycloud.client.jackson.SettlementEntryDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import net.minidev.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Settlement implements Entity {

    private String id;
    private String shortReference;
    private String status;
    private String type;
    private List<String> conversionIds = new ArrayList<>();
    @JsonDeserialize(using = SettlementEntryDeserializer.class)
    private Map<String, Entry> entries;
    private Date createdAt;
    private Date updatedAt;
    private Date releasedAt;
    private Date createdAtFrom;
    private Date createdAtTo;
    private Date updatedAtFrom;
    private Date updatedAtTo;
    private Date releasedAtFrom;
    private Date releasedAtTo;

    protected Settlement() { }

    public static Settlement create() {
        return new Settlement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shortReference, that.shortReference) &&
                Objects.equals(status, that.status) &&
                Objects.equals(conversionIds, that.conversionIds) &&
                Objects.equals(entries, that.entries) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(releasedAt, that.releasedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortReference, status, conversionIds, entries, createdAt, updatedAt, releasedAt);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\", \"shortReference\":\"%s\", \"status\"\"%s\", \"conversionIds\":\"%s\", \"entries\":[\"%s\"], \"createdAt\":%s, \"updatedAt\":\"%s\", \"releasedAt\":\"%s\"}",
                id, shortReference, status, conversionIds, entries, createdAt, updatedAt, releasedAt);
        /*ToDo: Replace toString hack with Map<String, Entry> deserialization */
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Entry {

        public Entry() { }

        public Entry(BigDecimal receiveAmount, BigDecimal sendAmount) {
            this.sendAmount = sendAmount;
            this.receiveAmount = receiveAmount;
        }

        private BigDecimal sendAmount;
        private BigDecimal receiveAmount;

        public BigDecimal getSendAmount() {
            return sendAmount;
        }

        public BigDecimal getReceiveAmount() {
            return receiveAmount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(sendAmount, entry.sendAmount) &&
                    Objects.equals(receiveAmount, entry.receiveAmount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sendAmount, receiveAmount);
        }

        @Override
        public String toString() {
            return new JSONObject()
                    .appendField("sendAmount", sendAmount)
                    .appendField("receiveAmount", receiveAmount)
                    .toString();
        }
    }
}
