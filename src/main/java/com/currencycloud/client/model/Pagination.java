package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination {

    private Integer totalEntries;
    private Integer totalPages;
    private Integer currentPage;
    private Integer perPage;
    private Integer previousPage;
    private Integer nextPage;
    private String order;
    private SortOrder orderAscDesc;

    public Pagination() {
    }

    public Pagination(Integer currentPage, Integer perPage, String order, SortOrder orderAscDesc) {
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.order = order;
        this.orderAscDesc = orderAscDesc;
    }

    public Integer getTotalEntries() {
        return totalEntries;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPage() {
        return getCurrentPage();
    }

    public void setPage(Integer page) {
        setCurrentPage(page);
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public SortOrder getOrderAscDesc() {
        return orderAscDesc;
    }

    public void setOrderAscDesc(SortOrder orderAscDesc) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        Map<String, Object> map = new HashMap<>();
        map.put("totalEntries", totalEntries);
        map.put("totalPages", totalPages);
        map.put("currentPage", currentPage);
        map.put("perPage", perPage);
        map.put("previousPage", previousPage);
        map.put("nextPage", nextPage);
        map.put("order", order);
        map.put("orderAscDesc", orderAscDesc);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }
}
