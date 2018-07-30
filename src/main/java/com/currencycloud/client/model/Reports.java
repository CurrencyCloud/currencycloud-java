package com.currencycloud.client.model;

import java.util.List;

public class Reports extends PaginatedData {

	private List<Report> reportRequests;

	public List<Report> getReportRequests() {
		return reportRequests;
	}

	 @Override
	    public String toString() {
	        return String.format("{\"reportRequests\":%s, \"pagination\":%s}", reportRequests, pagination);
	    }
}
