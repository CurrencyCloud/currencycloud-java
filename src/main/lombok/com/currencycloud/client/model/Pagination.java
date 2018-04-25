package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Pagination {

    private Integer totalEntries;
    private Integer totalPages;
    private Integer currentPage;
    private Integer perPage;
    private Integer previousPage;
    private Integer nextPage;
    private String order;
    private SortOrder orderAscDesc;

    public Integer getPage() {
        return getCurrentPage();
    }


    public Pagination() {
    }

    public Pagination(Integer currentPage, Integer perPage, String order, SortOrder orderAscDesc) {
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.order = order;
        this.orderAscDesc = orderAscDesc;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Pagination first() {
        return builder().limit(1).build();
    }

    public enum SortOrder {asc, desc;}

    public static class Builder {

        private Integer currentPage;

        private Integer perPage;
        private String order;
        private SortOrder orderAscDesc;
        protected Builder() {
        }

        public Builder pages(Integer currentPage, Integer perPage) {
            this.currentPage = currentPage;
            this.perPage = perPage;
            return this;
        }

        public Builder limit(Integer perPage) {
            this.perPage = perPage;
            return this;
        }

        public Builder order(String order) {
            this.order = order;
            return this;
        }

        public Builder order(String order, SortOrder orderAscDesc) {
            this.order = order;
            this.orderAscDesc = orderAscDesc;
            return this;
        }

        public Pagination build() {
            return new Pagination(currentPage, perPage, order, orderAscDesc);
        }
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("totalEntries", totalEntries)
                .appendField("totalPages", totalPages)
                .appendField("currentPage", currentPage)
                .appendField("perPage", perPage)
                .appendField("previousPage", previousPage)
                .appendField("nextPage", nextPage)
                .appendField("order", order)
                .appendField("orderAscDesc", orderAscDesc)
                .toString();
    }
}
