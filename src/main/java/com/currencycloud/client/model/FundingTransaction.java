package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundingTransaction implements Entity {
  private String id;
  private BigDecimal amount;
  private String currency;
  private String rail;
  private String additionalInformation;
  private String receivingAccountRoutingCode;
  private Date valueDate;
  private String receivingAccountNumber;
  private String receivingAccountIban;
  private Date createdAt;
  private Date updatedAt;
  private Date completedAt;
  private FundingTransactionSender sender;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getRail() {
    return rail;
  }

  public void setRail(String rail) {
    this.rail = rail;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getReceivingAccountRoutingCode() {
    return receivingAccountRoutingCode;
  }

  public void setReceivingAccountRoutingCode(String receivingAccountRoutingCode) {
    this.receivingAccountRoutingCode = receivingAccountRoutingCode;
  }

  public Date getValueDate() {
    return valueDate;
  }

  public void setValueDate(Date valueDate) {
    this.valueDate = valueDate;
  }

  public String getReceivingAccountNumber() {
    return receivingAccountNumber;
  }

  public void setReceivingAccountNumber(String receivingAccountNumber) {
    this.receivingAccountNumber = receivingAccountNumber;
  }

  public String getReceivingAccountIban() {
    return receivingAccountIban;
  }

  public void setReceivingAccountIban(String receivingAccountIban) {
    this.receivingAccountIban = receivingAccountIban;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Date completedAt) {
    this.completedAt = completedAt;
  }

  public FundingTransactionSender getSender() {
    return sender;
  }

  public void setSender(FundingTransactionSender sender) {
    this.sender = sender;
  }

  @Override
  public String toString() {
    final ObjectMapper objectMapper =
        new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("amount", amount);
    map.put("currency", currency);
    map.put("rail", rail);
    map.put("additionalInformation", additionalInformation);
    map.put("receivingAccountRoutingCode", receivingAccountRoutingCode);
    map.put("valueDate", valueDate);
    map.put("receivingAccountNumber", receivingAccountNumber);
    map.put("receivingAccountIban", receivingAccountIban);
    map.put("createdAt", createdAt);
    map.put("updatedAt", updatedAt);
    map.put("completedAt", completedAt);
    map.put("sender", sender);

    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return String.format("{\"error\": \"%s\"}", e.getMessage());
    }
  }
}
