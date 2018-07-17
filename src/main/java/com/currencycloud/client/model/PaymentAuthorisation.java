package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import net.minidev.json.JSONObject;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Payment.class)
public class PaymentAuthorisation implements Entity {

	private String id;
	private String paymentStatus;
	private boolean updated;
	private int authorisationStepsTaken;
	private int authorisationStepsRequired;
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
	public int getAuthorisationStepsTaken() {
		return authorisationStepsTaken;
	}
	public void setAuthorisationStepsTaken(int authorisationStepsTaken) {
		this.authorisationStepsTaken = authorisationStepsTaken;
	}
	public int getAuthorisationStepsRequired() {
		return authorisationStepsRequired;
	}
	public void setAuthorisationStepsRequired(int authorisationStepsRequired) {
		this.authorisationStepsRequired = authorisationStepsRequired;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorisationStepsRequired;
		result = prime * result + authorisationStepsTaken;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paymentStatus == null) ? 0 : paymentStatus.hashCode());
		result = prime * result + ((shortReference == null) ? 0 : shortReference.hashCode());
		result = prime * result + (updated ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentAuthorisation other = (PaymentAuthorisation) obj;
		if (authorisationStepsRequired != other.authorisationStepsRequired)
			return false;
		if (authorisationStepsTaken != other.authorisationStepsTaken)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paymentStatus == null) {
			if (other.paymentStatus != null)
				return false;
		} else if (!paymentStatus.equals(other.paymentStatus))
			return false;
		if (shortReference == null) {
			if (other.shortReference != null)
				return false;
		} else if (!shortReference.equals(other.shortReference))
			return false;
		if (updated != other.updated)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new JSONObject()
				.appendField("id", id)
				.appendField("paymentStatus", paymentStatus)
				.appendField("updated", updated)
				.appendField("authStepsTaken", authorisationStepsTaken)
				.appendField("authStepsRequired", authorisationStepsRequired)
				.appendField("shortReference", shortReference).toString();
	}


}
