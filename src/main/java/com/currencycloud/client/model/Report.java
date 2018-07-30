package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report implements Entity {

	String id;
	String shortreference;
	String reportType;
	String status;
	String expirationDate;
	String reportUrl;
	String accountId;
	String contactId;
	String createdAt;
	String updatedAt;

	@Override
	public String getId() {
		return id;
	}
}
