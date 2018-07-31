package com.currencycloud.client.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report implements Entity {

	String id;
	String shortReference;
	String reportType;
	String status;
	Date expirationDate;
	String reportUrl;
	String accountId;
	String contactId;
	Date createdAt;
	Date updatedAt;

	@Override
	public String getId() {
		return id;
	}
}
