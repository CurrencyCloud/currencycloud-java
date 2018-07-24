package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAuthorisation implements Entity {

	private String id;
	private String paymentStatus;
	private boolean updated;
	private int authStepsTaken;
	private int authStepsRequired;
	private String shortReference;
	private String error;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public int getAuthStepsTaken() {
		return authStepsTaken;
	}
	public void setAuthStepsTaken(int authorisationStepsTaken) {
		this.authStepsTaken = authorisationStepsTaken;
	}
	public int getAuthStepsRequired() {
		return authStepsRequired;
	}
	public void setAuthStepsRequired(int authorisationStepsRequired) {
		this.authStepsRequired = authorisationStepsRequired;
	}
	public String getShortReference() {
		return shortReference;
	}
	public void setShortReference(String shortReference) {
		this.shortReference = shortReference;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return new JSONObject()
				.appendField("id", id)
				.appendField("paymentStatus", paymentStatus)
				.appendField("updated", updated)
				.appendField("authStepsTaken", authStepsTaken)
				.appendField("authStepsRequired", authStepsRequired)
				.appendField("shortReference", shortReference).toString();
	}


}
