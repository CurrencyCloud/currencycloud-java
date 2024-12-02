package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermsAndConditionsAcceptance {
  private UUID acceptanceId;
  private Date acceptedAt;

  public UUID getAcceptanceId(){
    return acceptanceId;
  }

  public Date getAcceptedAt(){
    return acceptedAt;
  }
}
