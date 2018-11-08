package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReportRequests extends PaginatedData {

    @JsonProperty("report_requests")
    private List<ReportRequest> reportRequests;

    public List<ReportRequest> getReportRequests() {
        return reportRequests;
    }

    @Override
    public String toString() {
        return String.format("{\"report_requests\":%s, \"pagination\":%s}", reportRequests, pagination);
    }
}
